package android.security;

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 14.02.2018.
 */

import android.annotation.SuppressLint;
// https://github.com/square/okhttp/issues/2533#issuecomment-223093100
public class NetworkSecurityPolicy {
    private static final NetworkSecurityPolicy INSTANCE = new NetworkSecurityPolicy();
    @SuppressLint("NewApi") public static NetworkSecurityPolicy getInstance() { return INSTANCE; }
    public boolean isCleartextTrafficPermitted() { return true; }
}