package cl.blacksheep.streetmap.listener;

import com.google.android.gms.ads.AdListener;

public class MyAdListener extends AdListener {

    @Override
    public void onAdClosed() {
        super.onAdClosed();
    }

    @Override
    public void onAdFailedToLoad(int i) {
        super.onAdFailedToLoad(i);
    }

    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
    }

    @Override
    public void onAdClicked() {
        super.onAdClicked();
        System.out.println("click");
    }

    @Override
    public void onAdImpression() {
        super.onAdImpression();
    }
}
