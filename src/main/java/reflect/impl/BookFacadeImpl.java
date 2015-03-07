package reflect.impl;

import reflect.BookFacade;

public class BookFacadeImpl implements BookFacade {
    @Override
    public void addBook() {
        System.out.println("execute BookFacadeImpl.addBook()");
    }
}
