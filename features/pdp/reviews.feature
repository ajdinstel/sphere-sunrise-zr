Feature: product reviews on Product detail page

  Background:
    Given the default data set is loaded

  Scenario: reviews read
    Given  I am on the product detail page of the product "whatever-sku"
    Then I see the 4 reviews
      And they contain the text "whatever review text"


  Scenario: review creation
    Given I am on the product detail page of the product "whatever-sku"
    When I click on the button "create review"
      And enter the text "xyz"
      And click on the button "submit review"
    Then I see the the message "Thanks, we added your review ..."
