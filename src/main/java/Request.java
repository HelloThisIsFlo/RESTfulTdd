public class Request {

    private String url;
    private String service;


    public Request(String url) {
        this.url = url;
        extractService();
    }

    private void extractService(){
        String[] fragments = url.split("/");
        service = fragments[1];
    }

    public String getService() {
        return service;
    }

    public boolean isServiceValid() {
        return "transactionservice".equals(service);
    }
}
