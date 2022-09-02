Feature: Framework handling excel with Poiji

  @Framework @ExcelRead @nobrowser
  Scenario Outline: Basic Excel Handling - Row Wise [<Scenario>]
    Given User load excel data to POJO
    When User select scenario "<Scenario>"
    Then Read Excel data is mapped  with java object in Model BasicFrameworkModel
    Examples:
      | Scenario          |
      | TC1-BasicDataType |
