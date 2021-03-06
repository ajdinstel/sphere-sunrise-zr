package common.controllers;

public interface PageData {

    PageHeader getHeader();

    PageContent getContent();

    PageFooter getFooter();

    PageMeta getMeta();
}
