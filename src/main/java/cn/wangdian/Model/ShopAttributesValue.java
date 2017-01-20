package cn.wangdian.Model;

import javax.persistence.*;

/**
 * Created by 25065 on 2016/9/24.
 */
@Entity
@Table(name = "wd_shopattributesvalue")
public class ShopAttributesValue {

    @Id
    @GeneratedValue
    private Integer id;

    private String attributesValue;//属性值

//    private Integer shopAttributesId;//属性名Id

    private Integer isDel;//0存在，1删除

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "shopAttributes_id")
    private ShopAttributes shopAttributes;

    public ShopAttributesValue() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributesValue() {
        return attributesValue;
    }

    public void setAttributesValue(String attributesValue) {
        this.attributesValue = attributesValue;
    }

//    public Integer getShopAttributesId() {
//        return shopAttributesId;
//    }
//
//    public void setShopAttributesId(Integer shopAttributesId) {
//        this.shopAttributesId = shopAttributesId;
//    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public ShopAttributes getShopAttributes() {
        return shopAttributes;
    }

    public void setShopAttributes(ShopAttributes shopAttributes) {
        this.shopAttributes = shopAttributes;
    }
}
