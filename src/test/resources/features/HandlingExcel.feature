Feature: Framework handling excel with Poiji

  @Framework @ExcelRead @nobrowser @P0
  Scenario Outline: Basic Excel Handling - Row Wise [<Scenario>]
    Given User load excel data to POJO
    When User select scenario "<Scenario>"
    Then Excel data is mapped correctly with java object
    Examples:
      | Scenario          |
      | TC1-BasicDataType |
      | TC2-MissingData   |
      | TC3-MissingData   |
      | TC4-MissingData   |
      | TC5-MissingData   |
      | TC6-MissingData   |
      | TC7-MissingData   |
      | TC8-MissingData   |
      | TC9-MissingData   |
      | TC10-NegativeData |
