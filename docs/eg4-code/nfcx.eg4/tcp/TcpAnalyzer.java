package com.nfcx.eg4.tcp;

import com.nfcx.eg4.protocol.tcp.data.response.HeartBeatData;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.TCP_PROTOCOL;
import com.nfcx.eg4.protocol.tcp.dataframe.tcp.response.ResponseHeartBeatDataFrame;
import com.nfcx.eg4.tool.ProTool;
import java.util.Date;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class TcpAnalyzer extends SerialBuffer {
    private static final int PREFIX_CHECK_LENGTH = 2;
    private static final int PREFIX_LENGTH = 6;
    private static final int[] RECEIVE_PREFIX;
    private static String prefixText = "";
    private CommandSender commandSender;
    private int dataLength = 0;
    private StringBuffer dataBuffer = new StringBuffer();
    private boolean prefixValid = false;

    static {
        int[] iArr = {161, 26};
        RECEIVE_PREFIX = iArr;
        for (int i : iArr) {
            prefixText += ProTool.getStringFromHex(i);
        }
    }

    public TcpAnalyzer(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override // com.nfcx.eg4.tcp.SerialBuffer
    public synchronized void putChar(int i) {
        this.dataBuffer.append((char) i);
        checkData(i);
    }

    private void checkData(int i) {
        int length = this.dataBuffer.length();
        if (length == 2) {
            if (this.dataBuffer.toString().startsWith(prefixText)) {
                this.prefixValid = true;
                return;
            }
            return;
        }
        if (length > 2 && !this.prefixValid && i == 26) {
            int iIndexOf = this.dataBuffer.toString().indexOf(prefixText);
            if (iIndexOf > 0) {
                this.dataBuffer.delete(0, iIndexOf);
                this.prefixValid = true;
                return;
            }
            return;
        }
        if (length == 6 && this.prefixValid) {
            this.dataLength = (i * 256) + this.dataBuffer.charAt(length - 2);
        } else if (length == this.dataLength + 6 && this.prefixValid) {
            handleValidData();
        }
    }

    private void handleValidData() {
        System.out.println("Eg4 - " + new Date() + " - Valid: " + ProTool.showData(this.dataBuffer.toString()));
        char cCharAt = this.dataBuffer.charAt(7);
        if (cCharAt > 0) {
            switch (cCharAt) {
                case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256 /* 193 */:
                    handleHeartBeatData();
                    break;
                case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256 /* 194 */:
                    handleTranslate();
                    break;
                case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256 /* 195 */:
                    String string = this.dataBuffer.toString();
                    this.commandSender.putCommandWaitMap("read_datalog_" + ProTool.count(string.charAt(19), string.charAt(18)), string);
                    break;
                case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256 /* 196 */:
                    String string2 = this.dataBuffer.toString();
                    this.commandSender.putCommandWaitMap("write_datalog_" + ProTool.count(string2.charAt(19), string2.charAt(18)), string2);
                    break;
            }
        }
        StringBuffer stringBuffer = this.dataBuffer;
        stringBuffer.delete(0, stringBuffer.length());
        this.dataBuffer.trimToSize();
        this.prefixValid = false;
    }

    private void handleTranslate() {
        char cCharAt = this.dataBuffer.charAt(21);
        switch (cCharAt) {
            case '!':
                if (this.dataBuffer.charAt(32) == 1) {
                    this.commandSender.putCommandWaitMap("tcpUpdate_Prepare", this.dataBuffer.toString());
                    break;
                }
                break;
            case '\"':
                if (this.dataBuffer.charAt(32) == 1) {
                    this.commandSender.putCommandWaitMap("tcpUpdate_Send_" + ProTool.count(this.dataBuffer.charAt(34), this.dataBuffer.charAt(33)), this.dataBuffer.toString());
                    break;
                }
                break;
            case '#':
                if (this.dataBuffer.charAt(32) == 1) {
                    this.commandSender.putCommandWaitMap("tcpUpdate_Reset", this.dataBuffer.toString());
                    break;
                }
                break;
            default:
                int iCount = ProTool.count(this.dataBuffer.charAt(33), this.dataBuffer.charAt(32));
                char cCharAt2 = this.dataBuffer.charAt(34);
                if (cCharAt != 3 || cCharAt2 != 'P') {
                    if (cCharAt != 3 || cCharAt2 != 2) {
                        if (cCharAt != 3) {
                            if (cCharAt != 4 || cCharAt2 != 'P') {
                                if (cCharAt != 6) {
                                    if (cCharAt == 16) {
                                        this.commandSender.putCommandWaitMap("write_multi_06_" + iCount + "_" + ((int) cCharAt2), this.dataBuffer.toString());
                                        break;
                                    }
                                } else {
                                    this.commandSender.putCommandWaitMap("write_single_06", this.dataBuffer.toString());
                                    break;
                                }
                            } else if (iCount != 0) {
                                if (iCount != 40) {
                                    if (iCount == 80) {
                                        this.commandSender.putCommandWaitMap("read_04_3", this.dataBuffer.toString());
                                        break;
                                    }
                                } else {
                                    this.commandSender.putCommandWaitMap("read_04_2", this.dataBuffer.toString());
                                    break;
                                }
                            } else {
                                this.commandSender.putCommandWaitMap("read_04_1", this.dataBuffer.toString());
                                break;
                            }
                        } else {
                            this.commandSender.putCommandWaitMap("read_multi_03_" + iCount + "_" + ((int) cCharAt2), this.dataBuffer.toString());
                            break;
                        }
                    } else {
                        this.commandSender.putCommandWaitMap("read_single_03_" + iCount, this.dataBuffer.toString());
                        break;
                    }
                } else {
                    if (iCount == 0) {
                        this.commandSender.putCommandWaitMap("read_03_1", this.dataBuffer.toString());
                    } else if (iCount == 40) {
                        this.commandSender.putCommandWaitMap("read_03_2", this.dataBuffer.toString());
                    } else if (iCount == 80) {
                        this.commandSender.putCommandWaitMap("read_03_3", this.dataBuffer.toString());
                    }
                    this.commandSender.putCommandWaitMap("read_multi_03_" + iCount + "_" + ((int) cCharAt2), this.dataBuffer.toString());
                    break;
                }
                break;
        }
    }

    private void handleHeartBeatData() {
        this.commandSender.putCommandWaitMap("heart_beat", this.dataBuffer.toString());
        char cCharAt = this.dataBuffer.charAt(18);
        ResponseHeartBeatDataFrame responseHeartBeatDataFrame = new ResponseHeartBeatDataFrame(this.dataBuffer.charAt(2) == 2 ? TCP_PROTOCOL._02 : TCP_PROTOCOL._01);
        responseHeartBeatDataFrame.setData(new HeartBeatData(this.dataBuffer.substring(8, 18), cCharAt));
        this.commandSender.sendPureCommand(responseHeartBeatDataFrame);
    }
}