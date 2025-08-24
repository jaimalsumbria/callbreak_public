package com.waterbird.callbreak;

import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.waterbird.callbreak.R;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Tools {

    private static String algo = null;
    private static byte[] publicK;
    private static Cipher cipher;

    public static void init(Activity activity) {
        {
            ///okkkkkk
        }
    }

    public static void decorateRnb(Activity activity, AdView view){
        view.loadAd(new AdRequest.Builder().build());
    }

}
