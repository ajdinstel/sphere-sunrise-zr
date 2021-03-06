package io.sphere.sdk.facets;

import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.TermFacetAndFilterSearchModel;

import java.util.List;

/**
 * Interface that represents a facet, as used in a faceted search.
 * @param <T> type of the resource for this facet (e.g. ProductProjection)
 */
public interface Facet<T> {

    /**
     * Gets the string identifying this facet.
     * @return the key that identifies this facet
     */
    String getKey();

    /**
     * Gets the label displayed in the facet.
     * @return the label displayed in this facet
     */
    String getLabel();

    /**
     * Gets the type of this facet.
     * @return the type of this facet
     */
    FacetType getType();

    /**
     * Whether the facet can be displayed or not.
     * @return true if the facet can be displayed, false otherwise
     */
    boolean isAvailable();

    /**
     * Whether the facet count should be hidden or not.
     * @return true if the count should be hidden, false otherwise
     */
    boolean isCountHidden();

    /**
     * Gets the selected values for this facet used to filter.
     * @return the selected values
     */
    List<String> getSelectedValues();

    /**
     * Gets the untyped search model associated with this facet, representing the attribute path.
     * With this search model you can build facet and filter expressions for this attribute.
     * @return the untyped search model for this facet
     */
    TermFacetAndFilterSearchModel<T> getSearchModel();

    /**
     * Gets the filter expressions associated to this facet, according to the selected values, as needed to obtain a faceted search.
     * @return the filter expressions for this facet
     */
    FacetAndFilterExpression<T> getFacetedSearchExpression();

    /**
     * Gets a new instance of Facet with the same attributes as this, but with the facet result extracted from the given search result.
     * @param searchResult the search result containing the new facet result
     * @return a new instance with same attributes, but with the given facet result
     */
    Facet<T> withSearchResult(final PagedSearchResult<T> searchResult);

}
