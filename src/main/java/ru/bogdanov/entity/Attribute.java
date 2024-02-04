package ru.bogdanov.entity;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Attribute {
    public String title;
    public String slug;
    public String value;
    public Group group;
    public boolean isWebShort;
    public boolean isWebListing;
    public int sequence;
    public String featureDescription;
}
