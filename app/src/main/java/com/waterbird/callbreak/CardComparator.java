package com.waterbird.callbreak;

class CardComparator implements java.util.Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        return o1.getWeightage() - o2.getWeightage();
    }
}
