Feature: wishlist Product detail page

  Background:
    Given the default data set is loaded

  Scenario: anonymous visitors have a wishlist
    Given I am not logged in
      And I am on a "product detail page"
    When I click on the button "add to wishlist"
    Then I should get the confirmation "has been added to your wishlist"