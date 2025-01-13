package io.github.pourianof.ListMap;

import java.util.ArrayList;
import java.util.HashMap;

public class ListMap<K, V> extends HashMap<K, V> {
    public static void main(String[] args) {

    }
    private ArrayList<K> orderList = new ArrayList<>();

    @Override
    public V put(K key, V value){
        this.orderList.add(key);
        return super.put(key, value);
    }

    public V at(int index){
        return this.get(this.orderList.get(index));
    }
    @Override
    public void clear(){
        this.orderList.clear();
        super.clear();
    }

    public void putAll(ListMap<? extends K, ? extends V> map){
        this.orderList.addAll(map.orderList);
        super.putAll(map);
    }

    public ArrayList<V> getOrderedList(){
        ArrayList<V> orderedList =  new ArrayList<>();
        this.orderList.forEach(
                e-> orderedList.add(this.get(e))
        );
        return orderedList;
    }
    public ArrayList<V> getSubOrderedList (int startIndex, int endIndex){
        ArrayList<V> tempList = new ArrayList<>();
        for(int i = startIndex; i < endIndex; i++){
            tempList.add(
                    this.get(
                            this.orderList.get(i)
                    )
            );
        }
        return tempList;
    }
    public K getKeyByIndex(int index){
        return this.orderList.get(index);
    }
    public ArrayList<V> getSubOrderedList(int startIndex){
        return this.getSubOrderedList(startIndex, this.size());
    }
}
