package productcatalog.services;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTreeExtended;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import play.libs.F;
import productcatalog.controllers.SearchCriteria;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface ProductService {

    /**
     * Gets a list of Products from a PagedQueryResult
     * @param page the page to fetch
     * @param searchCriteria all information regarding the request parameters
     * @return A promise of a paged result of the search request
     */
    F.Promise<PagedSearchResult<ProductProjection>> searchProducts(final int page, final SearchCriteria searchCriteria);

    /**
     * Gets a product, uniquely identified by a locale and a slug
     * @param locale the locale in which you provide the slug
     * @param slug the slug
     * @return A Promise of an optionally found ProductProjection
     */
    F.Promise<Optional<ProductProjection>> findProductBySlug(final Locale locale, final String slug);

    /**
     * Gets a List of length numSuggestions of Products related somehow with the given product.
     * @param product the product to get suggestions for
     * @param numSuggestions the number of products the returned list should contain.
     *                       It might contain less if the requested number is greater
     *                       than the number of available products.
     * @return A Promise of the list of products without duplicates
     */
    F.Promise<List<ProductProjection>> getSuggestions(final ProductProjection product, final CategoryTreeExtended categoryTree,
                                                      final int numSuggestions);

    /**
     * Gets a List of length numSuggestions of Products from the given categories
     * @param categories the categories to get suggestions from
     * @param numSuggestions the number of products the returned list should contain.
     *                       It might contain less if the requested number is greater
     *                       than the number of available products.
     * @return A Promise of the list of products without duplicates
     */
    F.Promise<List<ProductProjection>> getSuggestions(final List<Category> categories, final int numSuggestions);
}
