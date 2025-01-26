package logic.youtube.model.search;

public class PageInfo {

  private Integer totalResults;
  private Integer resultsPerPage;

  public Integer getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }

  public PageInfo withTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
    return this;
  }

  public Integer getResultsPerPage() {
    return resultsPerPage;
  }

  public void setResultsPerPage(Integer resultsPerPage) {
    this.resultsPerPage = resultsPerPage;
  }

  public PageInfo withResultsPerPage(Integer resultsPerPage) {
    this.resultsPerPage = resultsPerPage;
    return this;
  }

}
