package productcatalog.models;

import common.contexts.UserContext;
import common.controllers.ReverseRouter;
import common.models.ProductDataConfig;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;

public class ProductThumbnailData {
    private boolean sale;
    private boolean _new;
    private boolean moreColors;
    private ProductData product;

    public ProductThumbnailData() {
    }

    public ProductThumbnailData(final ProductProjection product, final ProductVariant variant, final ProductDataConfig productDataConfig,
                                final UserContext userContext, final ReverseRouter reverseRouter, final CategoryTree categoryTreeNew) {
//        final String slug = product.getSlug().find(userContext.locale()).orElse("");
        this._new = product.getCategories().stream()
                .anyMatch(category -> categoryTreeNew.findById(category.getId()).isPresent());
        this.product = new ProductData(product, variant, productDataConfig, userContext, reverseRouter);
        this.sale = calculateIsSale(this.product);
//        this.moreColors = TODO get distinct from variant
    }

    private static boolean calculateIsSale(final ProductData productData) {
        return productData != null && productData.getVariant() != null && productData.getVariant().getPriceOld() != null;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(final boolean sale) {
        this.sale = sale;
    }

    public boolean isNew() {
        return _new;
    }

    public void setNew(final boolean _new) {
        this._new = _new;
    }

    public boolean isMoreColors() {
        return moreColors;
    }

    public void setMoreColors(final boolean moreColors) {
        this.moreColors = moreColors;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(final ProductData product) {
        this.product = product;
    }
}
