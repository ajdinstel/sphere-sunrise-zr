package purchase;

import common.contexts.UserContext;
import common.models.ProductDataConfig;
import common.controllers.ReverseRouter;
import io.sphere.sdk.carts.Cart;
import play.Configuration;
import play.i18n.Messages;

public class CheckoutShippingPageContent extends CheckoutPageContent {
    private CheckoutShippingFormBean shippingForm;


    public CheckoutShippingPageContent() {
    }

    public CheckoutShippingPageContent(final Cart cart, final Messages messages, final Configuration configuration,
                                       final ShippingMethods shippingMethods, final ProductDataConfig productDataConfig,
                                       final UserContext userContext, final ReverseRouter reverseRouter) {
        fillDefaults(cart, productDataConfig, userContext, reverseRouter);
        setShippingForm(new CheckoutShippingFormBean(cart, userContext, shippingMethods, messages, configuration));
    }

    public CheckoutShippingPageContent(final CheckoutShippingFormData filledForm, final Cart cart, final Messages messages,
                                       final Configuration configuration, final ShippingMethods shippingMethods,
                                       final ProductDataConfig productDataConfig, final UserContext userContext, final ReverseRouter reverseRouter) {
        fillDefaults(cart, productDataConfig, userContext, reverseRouter);
        setShippingForm(new CheckoutShippingFormBean(filledForm, userContext, shippingMethods, messages, configuration));
    }

    private void fillDefaults(final Cart cart, final ProductDataConfig productDataConfig,
                              final UserContext userContext, final ReverseRouter reverseRouter) {
        final StepWidgetBean stepWidget = new StepWidgetBean();
        stepWidget.setShippingStepActive(true);
        setStepWidget(stepWidget);
        setCart(new CartOrderBean(cart, productDataConfig, userContext, reverseRouter));
    }

    public CheckoutShippingFormBean getShippingForm() {
        return shippingForm;
    }

    public void setShippingForm(final CheckoutShippingFormBean shippingForm) {
        this.shippingForm = shippingForm;
    }

    @Override
    public String getAdditionalTitle() {
        return "checkout shipping";
    }
}
