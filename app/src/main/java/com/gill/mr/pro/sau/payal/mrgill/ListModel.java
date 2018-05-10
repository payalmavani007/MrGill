package com.gill.mr.pro.sau.payal.mrgill;

class ListModel {

    private String name;
    private String picture;
    private String description;
    private String url;

    public  void setName(String name1)
    {
        name = name1;
    }
    public void setDescription(String description1)
    {
        description=description1;
    }
    public void setUrl(String url1)
    {
        url=url1;
    }

    public void setPicture(String picture1)
    {
        picture=picture1;
    }
    public String getUrl()
    {
        return url;
    }


    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }



}
