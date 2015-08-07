Feature: wishlist Product detail page
  sells one specific product or solution

  Background:
    Given the default data set is loaded

  Scenario: product suggestions
    Given  I am on a "product detail page"
    When I click on the button "details"
    Then an accordion opens with the product attributes
