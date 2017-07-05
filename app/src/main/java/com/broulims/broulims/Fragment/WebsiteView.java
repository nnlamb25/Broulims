package com.broulims.broulims.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.broulims.broulims.R;

import static com.broulims.broulims.FragmentHolder.dpToPx;
import static com.broulims.broulims.FragmentHolder.hideBottom;
import static com.broulims.broulims.FragmentHolder.showBottom;


/**
 * This is the Website view for the app. It is accessible through the
 * bottom navigation and when opened, it brings up a splash screen
 * until the website loads. The site opens to "https://broulims.com/"
 * and allows the user to navigate it as if they were in a standard
 * web browser.
 * <br></br>
 * There is also a bottom navigation bar which you can use to
 * start our other main activities (such as the map view or list view)
 * <p></p>
 * Had help from:
 * http://www.codebind.com/android-tutorials-and-examples/convert-website-android-application-using-android-studio/
 *
 * @author Nathan Lamb
 */
public class WebsiteView extends Fragment
{
    private WebView webView;
    private String homePage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_website_view, container, false);
        webView = (WebView) view.findViewById(R.id.website_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        homePage = "https://broulims.com/";

        webView.loadUrl(homePage);
        //getActivity().getActionBar().hide();
        // Inflate the layout for this fragment
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = view.getRootView().getHeight() - view.getHeight();
                if (heightDiff > dpToPx(getContext(), 200)) {
                    hideBottom();
                }
                else
                {
                    showBottom();
                    //Log.i("key", "notshowing");
                }

            }
        });
        return view;
    }

    public String getCurrentURL()
    {
        return webView.getUrl().toString();
    }

    public String getHomePage()
    {
        return homePage;
    }

    public void setURL(String URL)
    {
        webView.loadUrl(URL);
    }
}
