package com.lux.luxcloud.view.main;

import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.lux.luxcloud.tool.Tool;
import com.lux.luxcloud.view.main.fragment.lv1.Lv112KRemoteSetFragment;
import com.lux.luxcloud.view.main.fragment.lv1.Lv1OffGridRemoteSetFragment;
import com.lux.luxcloud.view.main.fragment.lv1.Lv1RemoteSetFragment;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class ViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragmentList;
    private Set<Long> itemIds;
    private Lv112KRemoteSetFragment lv112KRemoteSetFragment;
    private Lv1OffGridRemoteSetFragment lv1OffGridRemoteSetFragment;
    private Lv1RemoteSetFragment lv1RemoteSetFragment;
    private RecyclerView recyclerView;

    public ViewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> list, Lv1RemoteSetFragment lv1RemoteSetFragment, Lv1OffGridRemoteSetFragment lv1OffGridRemoteSetFragment, Lv112KRemoteSetFragment lv112KRemoteSetFragment) {
        super(fragmentManager, lifecycle);
        this.itemIds = new HashSet();
        this.fragmentList = list;
        this.lv1RemoteSetFragment = lv1RemoteSetFragment;
        this.lv1OffGridRemoteSetFragment = lv1OffGridRemoteSetFragment;
        this.lv112KRemoteSetFragment = lv112KRemoteSetFragment;
    }

    public void switchRemoteSetFragment(int i) {
        if (this.fragmentList.size() > 3) {
            this.fragmentList.remove(3);
        }
        if (i == 3) {
            this.fragmentList.add(this.lv1OffGridRemoteSetFragment);
        } else if (i == 6) {
            this.fragmentList.add(this.lv112KRemoteSetFragment);
        } else {
            this.fragmentList.add(this.lv1RemoteSetFragment);
        }
        HashSet hashSet = new HashSet();
        Iterator<Fragment> it = this.fragmentList.iterator();
        while (it.hasNext()) {
            hashSet.add(Long.valueOf(it.next().hashCode()));
        }
        this.itemIds = hashSet;
        new Handler().post(new Runnable() { // from class: com.lux.luxcloud.view.main.ViewPagerAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.m618x578e81d();
            }
        });
    }

    /* renamed from: lambda$switchRemoteSetFragment$0$com-lux-luxcloud-view-main-ViewPagerAdapter, reason: not valid java name */
    /* synthetic */ void m618x578e81d() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Tool.sleep(10L);
            RecyclerView recyclerView = this.recyclerView;
            if (recyclerView != null && !recyclerView.isComputingLayout()) {
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    public Fragment createFragment(int i) {
        return this.fragmentList.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.fragmentList.size();
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return this.fragmentList.get(i).hashCode();
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    public boolean containsItem(long j) {
        return this.itemIds.contains(Long.valueOf(j));
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }
}