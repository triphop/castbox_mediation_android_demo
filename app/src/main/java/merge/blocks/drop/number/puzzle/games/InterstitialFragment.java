package merge.blocks.drop.number.puzzle.games;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fm.castbox.mediation.error.AdError;
import fm.castbox.mediation.interstitial.InterstitialAd;
import merge.blocks.drop.number.puzzle.games.databinding.FragmentInterstitialAdBinding;

public class InterstitialFragment extends Fragment {
    private final static String TAG = InterstitialFragment.class.getSimpleName();

    private FragmentInterstitialAdBinding binding;
    private Handler handler;
        private InterstitialAd interstitialAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInterstitialAdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler(Looper.getMainLooper());

        interstitialAd = new InterstitialAd(getActivity(), "m2block_inventory_32817576169963521");
        interstitialAd.setListener(new InterstitialAd.InterstitialAdListener() {
            @Override
            public void onError(@NonNull AdError adError) {
                Log.e(TAG, "error occurs - " + adError.toString());
            }

            @Override
            public void onAdLoaded() {
                Log.d(TAG, "ad loaded");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        interstitialAd.showAd();
                    }
                });
            }

            @Override
            public void onAdImpression(float ecpm) {
                Log.d(TAG, "ad impression");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "ad clicked");
            }

            @Override
            public void onAdDismissed() {
                Log.d(TAG, "ad dismissed");
            }
        });

        binding.buttonShowInterstitialAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAd.loadAd();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != interstitialAd) interstitialAd.destroy();
        interstitialAd = null;
    }
}
