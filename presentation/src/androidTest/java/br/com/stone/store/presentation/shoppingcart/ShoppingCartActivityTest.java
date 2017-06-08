package br.com.stone.store.presentation.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.matcher.RecyclerViewMatcher;
import br.com.stone.store.presentation.matcher.ViewChildMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by rrodovalho on 08/06/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShoppingCartActivityTest {

    static br.com.stone.store.presentation.matcher.RecyclerViewMatcher RecyclerViewMatcher =
            new RecyclerViewMatcher(R.id.shopping_cart_recyclerview);

    static String SHOPPING_CART_TWO_ITENS_JSON = "[\n" +
            "  {\n" +
            "    \"product\": {\n" +
            "      \"price\": \"7990\",\n" +
            "      \"seller\": \"Joana\",\n" +
            "      \"thumbnailImage\": \"https://cdn.awsli.com.br/1000x1000/21/21351/produto/7234148/55692a941d.jpg\",\n" +
            "      \"title\": \"Blusa Han Shot First\",\n" +
            "      \"zipCode\": \"13500-110\"\n" +
            "    },\n" +
            "    \"quantity\": 1\n" +
            "  },\n" +
            "  {\n" +
            "    \"product\": {\n" +
            "      \"price\": \"7990\",\n" +
            "      \"seller\": \"João da Silva\",\n" +
            "      \"thumbnailImage\": \"https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg\",\n" +
            "      \"title\": \"Blusa do Imperio\",\n" +
            "      \"zipCode\": \"78993-000\"\n" +
            "    },\n" +
            "    \"quantity\": 3\n" +
            "  }\n" +
            "]";

    @Rule
    public ActivityTestRule<ShoppingCartActivity> testRule =
            new ActivityTestRule<ShoppingCartActivity>(ShoppingCartActivity.class,true,false){

                @Override
                protected void afterActivityLaunched() {
                    idlingResource = testRule.getActivity().getCountingIdlingResource();
                    Espresso.registerIdlingResources(idlingResource);
                }

            };

    IdlingResource idlingResource;


    private void checkTextInRecyclerView(int position, String text){
        onView(RecyclerViewMatcher.atPosition(position))
                .check(matches(hasDescendant(withText(text))));
    }

    @After
    public void tearDown() throws Exception {
        if (idlingResource != null){
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

    private Intent createStarterIntent(String shoppingCartJson){

        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, ShoppingCartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ShoppingCartActivity.INTENT_EXTRA_KEY,shoppingCartJson);
        result.putExtras(bundle);

        return result;
    }

    private void checkItemCounterNumberPickerValue(int position, String value){
        ViewInteraction textView = onView(
                allOf(withId(R.id.text_value), withText(value),
                        ViewChildMatcher.childAtPosition(
                                allOf(withId(R.id.product_item_counter_number_picker),
                                        ViewChildMatcher.childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(value)));
    }

    @Test
    public void shoppingCartPopulated() throws Exception{

        testRule.launchActivity(createStarterIntent(SHOPPING_CART_TWO_ITENS_JSON));

        checkTextInRecyclerView(0,"Blusa Han Shot First");

        checkItemCounterNumberPickerValue(0,"1");
        checkTextInRecyclerView(1,"Blusa do Imperio");
        checkItemCounterNumberPickerValue(1,"3");
    }

    @Test
    public void emptyShoppingCart() throws Exception{
        testRule.launchActivity(createStarterIntent("[]"));
        onView(withId(R.id.empty_cart_imageview)).check(matches(isDisplayed()));
        onView(withId(R.id.shopping_cart_recyclerview)).check(matches(not(isDisplayed())));
    }



}