package com.broulims.broulims.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.broulims.broulims.R;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_website_view, container, false);
        webView = (WebView) view.findViewById(R.id.website_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://broulims.com/");
        //getActivity().getActionBar().hide();
        // Inflate the layout for this fragment
        return view;
    }
}
