package merge.blocks.drop.number.puzzle.games;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fm.castbox.mediation.error.AdError;
import fm.castbox.mediation.widget.AdView;
import merge.blocks.drop.number.puzzle.games.databinding.FragmentBannerBinding;


public class BannerFragment extends Fragment {
    private final static String TAG = "Banner";

    private FragmentBannerBinding binding;
    private AdView adView;

    private Handler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler = new Handler();

        binding.buttonAdloadStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear all child views
                binding.adBannerContainer.removeAllViews();

                LayoutInflater inflater = (LayoutInflater)getContext()
                        .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // destroy previous adView if needed
                if (null != adView) adView.destroy();

                adView = (AdView) inflater.inflate(R.layout.banner_ad, null);
                adView.setAdListener(new AdView.AdListener() {
                    @Override
                    public void onError(@NonNull AdError adError) {
                        Log.d(TAG, "failed to load ad due to " + adError.toString());
                    }

                    @Override
                    public void onInventoryReady() {
                        Log.d(TAG, "ad inventory ready");

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "static ad is shown", Toast.LENGTH_SHORT).show();
                                adView.showAd(getActivity());
                            }
                        });
                    }

                    @Override
                    public void onAdLoaded() {
                        Log.d(TAG, "ad loaded");
                    }

                    @Override
                    public void onAdImpression(float ecpm) {
                        Log.d(TAG, "ad is present");
                    }

                    @Override
                    public void onAdClicked() {
                        Log.d(TAG, "ad has been clicked");
                    }
                });

                binding.adBannerContainer.addView(adView);
                adView.loadAd();

            }
        });

        binding.buttonAdloadDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear all child views
                binding.adBannerContainer.removeAllViews();

                // destroy previous adView if needed
                if (null != adView) adView.destroy();

                try {
                    adView = new AdView(getActivity(), "m2block_inventory_32389367225909249");

                    binding.adBannerContainer.addView(adView);

                    adView.setAdListener(new AdView.AdListener() {
                        @Override
                        public void onError(@NonNull AdError adError) {
                            Log.d(TAG, "failed to load ad due to " + adError.toString());
                        }

                        @Override
                        public void onInventoryReady() {
                            Log.d(TAG, "ad inventory ready");

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "dynamic ad is shown", Toast.LENGTH_SHORT).show();
                                    adView.showAd(getActivity());
                                }
                            });
                        }

                        @Override
                        public void onAdLoaded() {
                            Log.d(TAG, "ad loaded");
                        }

                        @Override
                        public void onAdImpression(float ecpm) {
                            Log.d(TAG, "ad is present");
                        }

                        @Override
                        public void onAdClicked() {
                            Log.d(TAG, "ad has been clicked");
                        }
                    });

                    adView.loadAd();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != adView) adView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != adView) adView.resume();
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");

        if (null != adView) adView.destroy();
        adView = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != adView) adView.destroy();

        adView = null;
        binding = null;
    }


}