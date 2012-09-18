package com.robustaweb.library.rest.etag;


/**
 * User: Nicolas
 * Date: 18/09/12
 * Time: 14:34
 */
public interface Etag {

    /**
     * Etags are Representation Specific ;
     * By default, returns this MD5 of the representation, wich may take some time for heavy representations.
     * If you use seriously Etags, you should overwrite this method. hashcode() method may also return your EtagObject value.
     * @return EtagValue
     */
    public String getEtagValue();

    public long getLastModified();

    public void setLastModified(Long date);

    public void setEmptyValue(String value);
    public String getEmptyValue();

}
