package com.company;

import java.util.HashSet;

/**
 * Created by ymh on 2016/9/7.
 */
public class ObserverTest {
    public static void main(String[] args) {
        Product product = new Product("加多宝", 5);
        HttpObserver httpObserver = new HttpObserver();
        EmailObserver emailObserver = new EmailObserver();
        product.setObserver(httpObserver);
        product.setObserver(emailObserver);
        System.out.println("=============第一次价格变化============");
        product.setPrice(20);
        httpObserver.removeObserver(product);
        System.out.println("=============第二次价格变化============");
        product.setPrice(100);
    }
}

abstract class Observer {
    public abstract void onChange(Product product);

    public abstract void removeObserver(Product product);

}

class HttpObserver extends Observer {

    @Override
    public void onChange(Product product) {
        System.out.println("网站价格改变了，HTTP收到提醒了" + product.getPrice());
    }

    @Override
    public void removeObserver(Product product) {
        product.getHashSet().remove(this);
    }
}

class EmailObserver extends Observer {

    @Override
    public void onChange(Product product) {
        System.out.println("网站价格改变了，Email收到提醒了" + product.getPrice());
    }

    @Override
    public void removeObserver(Product product) {
        product.getHashSet().remove(this);
    }

}

class Product {
    private String name;
    private int price;
    private HashSet<Observer> hashSet = new HashSet<>();


    public void setObserver(Observer observer) {
        hashSet.add(observer);
    }


    public void notifyAllObservers() {
        for (Observer observer : hashSet) {
            observer.onChange(this);
        }
    }

    public HashSet<Observer> getHashSet() {
        return hashSet;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
        notifyAllObservers();
    }
}
