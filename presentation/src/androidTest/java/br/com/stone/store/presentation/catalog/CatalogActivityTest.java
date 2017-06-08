package br.com.stone.store.presentation.catalog;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.stone.store.data.repository.remote.ServerUrl;
import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.catalog.matcher.RecyclerViewMatcher;
import br.com.stone.store.presentation.catalog.matcher.ViewChildMatcher;
import br.com.stone.store.presentation.shoppingcart.ShoppingCartActivity;
import br.com.stone.store.presentation.transactionhistory.TransactionHistoryActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by rrodovalho on 07/06/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CatalogActivityTest {

    static RecyclerViewMatcher RecyclerViewMatcher =
            new RecyclerViewMatcher(R.id.catalog_recyclerview);

    static String RESPONSE_TWO_ITENS_JSON = "[\n" +
            "  {\n" +
            "    \"title\": \"Blusa do Imperio\",\n" +
            "    \"price\": 7990,\n" +
            "    \"zipcode\": \"78993-000\",\n" +
            "    \"seller\": \"Jo\\u00e3o da Silva\",\n" +
            "    \"thumbnailHd\": \"https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg\",\n" +
            "    \"date\": \"26/11/2015\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"title\": \"Blusa Han Shot First\",\n" +
            "    \"price\": 7990,\n" +
            "    \"zipcode\": \"13500-110\",\n" +
            "    \"seller\": \"Joana\",\n" +
            "    \"thumbnailHd\": \"https://cdn.awsli.com.br/1000x1000/21/21351/produto/7234148/55692a941d.jpg\",\n" +
            "    \"date\": \"26/11/2015\"\n" +
            "  }]";

    @Rule
    public ActivityTestRule<CatalogActivity> testRule =
            new ActivityTestRule<CatalogActivity>(CatalogActivity.class,true, false){

                @Override
                protected void afterActivityLaunched() {
                    idlingResource = testRule.getActivity().getCountingIdlingResource();
                    Espresso.registerIdlingResources(idlingResource);
                }
            };

    IdlingResource idlingResource;

    @After
    public void tearDown() throws Exception {
        if (idlingResource != null){
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

    @Test
    public void showCatalogExpectedSuccess() throws Exception {

        MockWebServer server = new MockWebServer();
        server.start();
        ServerUrl.STORE_PRODUCTS_URL = server.url("/").toString();

        server.enqueue(new MockResponse().setBody(RESPONSE_TWO_ITENS_JSON));

        Intent intent = new Intent();
        testRule.launchActivity(intent);

        onView(withId(R.id.loading_framelayout)).check(matches(not(isDisplayed())));
        checkDisplayedSuccessScreen();

        server.shutdown();
    }

    private void checkTextInRecyclerView(int position, String text){
        onView(RecyclerViewMatcher.atPosition(position))
                .check(matches(hasDescendant(withText(text))));
    }

    private void checkDisplayedErrorScreen(){
        onView(withId(R.id.error_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.error_imageview)).check(matches(isDisplayed()));
        onView(withId(R.id.error_description_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.retryButton)).check(matches(isDisplayed()));
    }

    private void checkDisplayedSuccessScreen(){
        checkTextInRecyclerView(0,"Blusa do Imperio");
        checkTextInRecyclerView(0,"R$79,90");
        checkTextInRecyclerView(0,"João da Silva");
        checkTextInRecyclerView(1,"Blusa Han Shot First");
        checkTextInRecyclerView(1,"R$79,90");
        checkTextInRecyclerView(1,"Joana");
    }

    @Test
    public void showCatalogExpectedFailure() throws Exception {

        MockWebServer server = new MockWebServer();
        server.start();
        ServerUrl.STORE_PRODUCTS_URL = server.url("/").toString();
        server.enqueue(new MockResponse().setResponseCode(404).setBody("{}"));

        Intent intent = new Intent();
        testRule.launchActivity(intent);

        checkDisplayedErrorScreen();

        server.shutdown();
    }


    @Test
    public void retryGetCatalogExpectedSuccess() throws Exception {

        MockWebServer server = new MockWebServer();
        server.start();
        ServerUrl.STORE_PRODUCTS_URL = server.url("/").toString();
        server.enqueue(new MockResponse().setResponseCode(404).setBody("{}"));
        server.enqueue(new MockResponse().setBody(RESPONSE_TWO_ITENS_JSON));

        Intent intent = new Intent();
        testRule.launchActivity(intent);

        checkDisplayedErrorScreen();
        onView(withId(R.id.retryButton)).perform(click());
        onView(withId(R.id.error_imageview)).check(matches(not(isDisplayed())));

        checkDisplayedSuccessScreen();

        server.shutdown();
    }

    @Test
    public void showShoppingCartScreen() throws Exception {

        Intents.init();
        testRule.launchActivity(new Intent());

        onView(withId(R.id.menu_shopping_cart)).perform(click());

        intended(hasComponent(ShoppingCartActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void showTransactionHistoryScreen() throws Exception {

        Intents.init();
        testRule.launchActivity(new Intent());

        onView(withId(R.id.menu_transaction_history)).perform(click());

        intended(hasComponent(TransactionHistoryActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void updateCartItemCounter() throws Exception {

        MockWebServer server = new MockWebServer();
        server.start();
        ServerUrl.STORE_PRODUCTS_URL = server.url("/").toString();
        server.enqueue(new MockResponse().setBody(RESPONSE_TWO_ITENS_JSON));

        Intent intent = new Intent();
        testRule.launchActivity(intent);

        ViewInteraction shoppingCartBadgeCounter = onView(
                allOf(withId(R.id.menu_badge),
                        ViewChildMatcher.childAtPosition(
                                allOf(withId(R.id.menu_shopping_cart),
                                        ViewChildMatcher.childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.support.v7.widget.LinearLayoutCompat.class),
                                                1)),
                                1),
                        isDisplayed()));

        shoppingCartBadgeCounter.check(matches(withText("0")));

        onView(RecyclerViewMatcher
                .atPositionOnView(0, R.id.add_to_cart_button))
                .perform(click());

        shoppingCartBadgeCounter.check(matches(withText("1")));

        onView(RecyclerViewMatcher
                .atPositionOnView(1, R.id.add_to_cart_button))
                .perform(click());

        shoppingCartBadgeCounter.check(matches(withText("2")));

        onView(RecyclerViewMatcher
                .atPositionOnView(1, R.id.add_to_cart_button))
                .perform(click());

        shoppingCartBadgeCounter.check(matches(withText("2")));
    }

}