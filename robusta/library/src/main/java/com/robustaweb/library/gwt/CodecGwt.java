/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.gwt;

import com.google.gwt.core.client.JavaScriptObject;
import com.robustaweb.library.commons.exception.CodecException;
import com.robustaweb.library.security.Codec;

/**
 * Contains public domaine code, and uses a GPL code
 * The class need a requet from browser toward md5 source code : http://pajhome.org.uk/crypt/md5/md5.js
 * If a firewall impeach this connexion, you should contact Paj and ask for a grant of inserting directly its code.
 *
 * //PAJ for SHA-1 : http://pajhome.org.uk/crypt/md5/sha1.html
 *
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class CodecGwt implements Codec {


    /**
     * Uses Public Domain code
     * @return
     */
    private native static JavaScriptObject getBase64Object()/*-{
    var Base64 = {


    _keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",


    encode : function (input) {
    var output = "";
    var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
    var i = 0;

    input = Base64._utf8_encode(input);

    while (i < input.length) {

    chr1 = input.charCodeAt(i++);
    chr2 = input.charCodeAt(i++);
    chr3 = input.charCodeAt(i++);

    enc1 = chr1 >> 2;
    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
    enc4 = chr3 & 63;

    if (isNaN(chr2)) {
    enc3 = enc4 = 64;
    } else if (isNaN(chr3)) {
    enc4 = 64;
    }

    output = output +
    this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
    this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

    }

    return output;
    },


    decode : function (input) {
    var output = "";
    var chr1, chr2, chr3;
    var enc1, enc2, enc3, enc4;
    var i = 0;

    input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

    while (i < input.length) {

    enc1 = this._keyStr.indexOf(input.charAt(i++));
    enc2 = this._keyStr.indexOf(input.charAt(i++));
    enc3 = this._keyStr.indexOf(input.charAt(i++));
    enc4 = this._keyStr.indexOf(input.charAt(i++));

    chr1 = (enc1 << 2) | (enc2 >> 4);
    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
    chr3 = ((enc3 & 3) << 6) | enc4;

    output = output + String.fromCharCode(chr1);

    if (enc3 != 64) {
    output = output + String.fromCharCode(chr2);
    }
    if (enc4 != 64) {
    output = output + String.fromCharCode(chr3);
    }

    }

    output = Base64._utf8_decode(output);

    return output;

    },


    _utf8_encode : function (string) {
    string = string.replace(/\r\n/g,"\n");
    var utftext = "";

    for (var n = 0; n < string.length; n++) {

    var c = string.charCodeAt(n);

    if (c < 128) {
    utftext += String.fromCharCode(c);
    }
    else if((c > 127) && (c < 2048)) {
    utftext += String.fromCharCode((c >> 6) | 192);
    utftext += String.fromCharCode((c & 63) | 128);
    }
    else {
    utftext += String.fromCharCode((c >> 12) | 224);
    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
    utftext += String.fromCharCode((c & 63) | 128);
    }

    }

    return utftext;
    },


    _utf8_decode : function (utftext) {
    var string = "";
    var i = 0;
    var c = c1 = c2 = 0;

    while ( i < utftext.length ) {

    c = utftext.charCodeAt(i);

    if (c < 128) {
    string += String.fromCharCode(c);
    i++;
    }
    else if((c > 191) && (c < 224)) {
    c2 = utftext.charCodeAt(i+1);
    string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
    i += 2;
    }
    else {
    c2 = utftext.charCodeAt(i+1);
    c3 = utftext.charCodeAt(i+2);
    string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
    i += 3;
    }

    }

    return string;
    }

    }; return Base64;
    }-*/;


    private native static String hex_md5Native(String strToEncode)/*-{
    
         var result = $wnd.hex_md5(strToEncode);
    return result;
    }-*/;

    private native static String encodeB64(String toEncode, JavaScriptObject base64Object)/*-{
    return base64Object.encode(toEncode);
     }-*/;

    private native static String decodeB64(String toEncode, JavaScriptObject base64Object)/*-{
    return base64Object.decode(toEncode);
    }-*/;


    @Override
    public String encodeBase64(String toEncode) {
       // return null;
        return encodeB64(toEncode, getBase64Object());
    }


    @Override
    public String decodeBase64(String encoded) {
        //return null;
        return decodeB64(encoded, getBase64Object());
    }

    /**
     * The web page MUST contain a field toward the md5.js  from http://pajhome.org.uk/crypt/md5/md5.js
     * or other  md5 javascript file that get a valid <strong>hex_md5()</strong> function
     *returns the correct md5 encoded string
     */
    @Override
    public String encodeMD5(String toEncode) {
        System.out.println("encoding md5:"+toEncode);
        return hex_md5Native(toEncode);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getUsername(String b64String) throws CodecException {
        String decoded = decodeBase64(b64String);
        int index = decoded.indexOf(":");
        if (index < 0) {
            throw new CodecException("can't find ':' character in decoded credentials for :" + decoded);
        }

        return decoded.substring(0, index);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getPassword(String b64String) throws CodecException {
        String decoded = decodeBase64(b64String);
        int index = decoded.indexOf(":");
        if (index < 0) {
            throw new CodecException("can't find ':' character in decoded credentials for :" + decoded);
        }

        return decoded.substring(index + 1);
    }


  
}
