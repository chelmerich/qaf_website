package com.queerartfilm.film;

import com.google.appengine.api.datastore.PhoneNumber;
import org.json.JSONObject;
import org.json.JSONString;

public enum Venue implements JSONString {

    IFC_CENTER("IFC Center", "323 Sixth Avenue",
    "at West 3rd St", new PhoneNumber("212-924-7771")),
    _92Y_TRIBECA("92YTribeca", "200 Hudson St.", "", new PhoneNumber("212-601-1000")),;
    
    private String title;
    private String address1;
    private String address2;
    private PhoneNumber phoneNumber;

    private Venue(String title, String address1, String address2, PhoneNumber phoneNumber) {
        this.title = title;
        this.address1 = address1;
        this.address2 = address2;
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getName() {
        return name();
    }
    @Override
    public String toJSONString() {
        String result = null;
        JSONObject json = new JSONObject();
        try {
            json.put("title", this.getTitle());
            json.put("address1", this.getAddress1());
            json.put("address2", getAddress2());
            json.put("phoneNumber", this.phoneNumber.getNumber());
                result = json.toString();
        } catch (Exception e) {
        }

        return result;
    }

    public static void main(String[] args) {
        Venue v = IFC_CENTER;
        System.out.println(v.toJSONString());
    }
}
