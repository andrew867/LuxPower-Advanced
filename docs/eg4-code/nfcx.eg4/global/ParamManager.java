package com.nfcx.eg4.global;

import com.nfcx.eg4.global.bean.property.Property;
import java.util.ArrayList;
import java.util.List;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class ParamManager {
    private static List<Property> phase1LineChartParams = new ArrayList();
    private static List<Property> phase3LineChartParams = new ArrayList();
    private static List<Property> phase1EnergyChartParams = new ArrayList();
    private static List<Property> phase3EnergyChartParams = new ArrayList();

    static {
        phase1LineChartParams.add(new Property("vpv1", "Vpv1(V)"));
        phase1LineChartParams.add(new Property("vpv2", "Vpv2(V)"));
        phase1LineChartParams.add(new Property("ppv1", "Ppv1(W)"));
        phase1LineChartParams.add(new Property("ppv2", "Ppv2(W)"));
        phase1LineChartParams.add(new Property("soc", "SOC(%)"));
        phase1LineChartParams.add(new Property("vacr", "Vacr(V)"));
        phase1LineChartParams.add(new Property("qac", "Qac(Var)"));
        phase1LineChartParams.add(new Property("vepsr", "Vepsr(V)"));
        phase1LineChartParams.add(new Property("feps", "Feps(Hz)"));
        phase1LineChartParams.add(new Property("peps", "Peps(W)"));
        phase1LineChartParams.add(new Property("seps", "Seps(VA)"));
        phase1LineChartParams.add(new Property("pToGrid", "pToGrid(W)"));
        phase3LineChartParams.add(new Property("vpv1", "Vpv1(V)"));
        phase3LineChartParams.add(new Property("vpv2", "Vpv2(V)"));
        phase3LineChartParams.add(new Property("vpv3", "Vpv3(V)"));
        phase3LineChartParams.add(new Property("ppv1", "Ppv1(W)"));
        phase3LineChartParams.add(new Property("ppv2", "Ppv2(W)"));
        phase3LineChartParams.add(new Property("ppv3", "Ppv3(W)"));
        phase3LineChartParams.add(new Property("soc", "SOC(%)"));
        phase3LineChartParams.add(new Property("vacr", "Vacr(V)"));
        phase3LineChartParams.add(new Property("vacs", "Vacs(V)"));
        phase3LineChartParams.add(new Property("vact", "Vact(V)"));
        phase3LineChartParams.add(new Property("qac", "Qac(Var)"));
        phase3LineChartParams.add(new Property("vepsr", "Vepsr(V)"));
        phase3LineChartParams.add(new Property("vepss", "Vepss(V)"));
        phase3LineChartParams.add(new Property("vepst", "Vepst(V)"));
        phase3LineChartParams.add(new Property("feps", "Feps(Hz)"));
        phase3LineChartParams.add(new Property("peps", "Peps(W)"));
        phase3LineChartParams.add(new Property("seps", "Seps(VA)"));
        phase3LineChartParams.add(new Property("pToGrid", "pToGrid(W)"));
        phase1EnergyChartParams.add(new Property("ePv1Day", "E_pv1(kWh)"));
        phase1EnergyChartParams.add(new Property("ePv2Day", "E_pv2(kWh)"));
        phase1EnergyChartParams.add(new Property("eInvDay", "E_inv(kWh)"));
        phase1EnergyChartParams.add(new Property("eRecDay", "E_rec(kWh)"));
        phase1EnergyChartParams.add(new Property("eChgDay", "E_charge(kWh)"));
        phase1EnergyChartParams.add(new Property("eDisChgDay", "E_discharge(kWh)"));
        phase1EnergyChartParams.add(new Property("eEpsDay", "E_eps(kWh)"));
        phase1EnergyChartParams.add(new Property("eToGridDay", "EnergyToGrid(kWh)"));
        phase1EnergyChartParams.add(new Property("eToUserDay", "EnergyToUser(kWh)"));
        phase3EnergyChartParams.add(new Property("ePv1Day", "E_pv1(kWh)"));
        phase3EnergyChartParams.add(new Property("ePv2Day", "E_pv2(kWh)"));
        phase3EnergyChartParams.add(new Property("ePv3Day", "E_pv3(kWh)"));
        phase3EnergyChartParams.add(new Property("eInvDay", "E_inv(kWh)"));
        phase3EnergyChartParams.add(new Property("eRecDay", "E_rec(kWh)"));
        phase3EnergyChartParams.add(new Property("eChgDay", "E_charge(kWh)"));
        phase3EnergyChartParams.add(new Property("eDisChgDay", "E_discharge(kWh)"));
        phase3EnergyChartParams.add(new Property("eEpsDay", "E_eps(kWh)"));
        phase3EnergyChartParams.add(new Property("eToGridDay", "EnergyToGrid(kWh)"));
        phase3EnergyChartParams.add(new Property("eToUserDay", "EnergyToUser(kWh)"));
    }

    public static List<Property> getLineChartParams(int i) {
        if (i == 1) {
            return phase1LineChartParams;
        }
        return phase3LineChartParams;
    }

    public static List<Property> getEnergyChartParams(int i) {
        if (i == 1) {
            return phase1EnergyChartParams;
        }
        return phase3EnergyChartParams;
    }
}