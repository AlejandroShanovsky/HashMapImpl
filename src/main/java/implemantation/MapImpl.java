package implemantation;

import interfaces.Map;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapImpl<K, V> implements Map<K, V> {

    private Entry<K, V>[] hashTable;
    private int size = 0;
    private float mapLength;

    public MapImpl() {
        hashTable = new Entry[16];
        mapLength = hashTable.length * 0.75f;
    }

    private int hash(final K key) {
        int hash = 37;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }

    public boolean put(K key, V value) {
        if (size + 1 >= mapLength) {
            mapLength *= 2;
            hashTableIncrease();
        }
        Entry<K, V> entry = new Entry<K, V>(key, value);
        int index = hash(key);
        if (hashTable[index] == null) {
            hashTable[index] = new Entry<K, V>(null, null);
            hashTable[index].getEntryes().add(entry);
            size++;
            return true;
        }

        List<Entry<K, V>> entryList = hashTable[index].getEntryes();

        for (Entry<K, V> entryFromList : entryList) {
            if (keyAlreadyExist(entry, entryFromList) ||
            collisionHandler(entryFromList, entry, entryList )) {
                return true;
            }
        }
        return false;
    }

    public V get(final K key) {
        int index = hash(key);
        if (index < hashTable.length &&
                hashTable[index] != null) {
            List<Entry<K, V>> list = hashTable[index].getEntryes();
            for (Entry<K, V> entry : list) {
                if (key.equals(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public int size() {
        return this.size;
    }

    public Iterator<V> iterator() {
        return new Iterator<V>() {
            int count = 0;
            int values = 0;
            Iterator<Entry<K, V>> subIterator = null;

            public boolean hasNext() {
                if (values == size) return false;

                if (subIterator == null || !subIterator.hasNext()) {
                    if (moveToNextCell()) {
                        subIterator = hashTable[count].getEntryes().iterator();
                    } else {
                        return false;
                    }
                }
                return subIterator.hasNext();
            }

            private boolean moveToNextCell() {
                count++;
                while (count < hashTable.length && hashTable[count] == null) {
                    count++;
                }
                return count < hashTable.length && hashTable[count] != null;
            }

            public V next() {
                values++;
                return subIterator.next().getValue();
            }

            public void remove() {
            }

        };
    }

    private boolean keyAlreadyExist(
            final Entry<K, V> entryFromList,
            final Entry<K, V> entry) {

        if (entry.getKey().equals(entryFromList.getKey()) &&
                !entry.getValue().equals(entryFromList.getValue()))
        {
            entryFromList.setValue(entry.getValue());
            return true;
        }
        return false;
    }

    private boolean collisionHandler(
            final Entry<K, V> entryFromList,
            final Entry<K, V> entry,
            final List<Entry<K, V>> entries) {
        if (entry.hashCode() == entryFromList.hashCode() &&
            entry.getKey() != entryFromList.getKey() &&
            entry.getValue() != entry.getValue()) {
            entries.add(entry);
            size++;
            return true;
        }
        return false;
    }

    private void hashTableIncrease() {
        Entry<K, V>[] oldHashTable = hashTable;
        hashTable = new Entry[oldHashTable.length * 2];
        size = 0;
        for (Entry<K, V> entry : oldHashTable) {
            if (entry != null) {
                for (Entry<K, V> entryFromList : entry.getEntryes()) {
                    this.put(entryFromList.getKey(), entryFromList.getValue());
                }
            }
        }
    }

    @Getter
    @Setter
    private class Entry<K, V> {
        private K key;
        private V value;
        private int hash;
        private List<Entry<K, V>> entryes;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            entryes = new ArrayList<Entry<K, V>>();
        }

        @Override
        public int hashCode() {
            hash = 37;
            hash = hash * 17 + key.hashCode();
            return hash;
        }


        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (this == obj) return true;
            if (obj instanceof Entry) {
                Entry<K, V> entry = (Entry) obj;
                return (this.key == entry.getKey() &&
                        this.value == entry.getValue() ||
                        this.hash == entry.hashCode());
            }
            return false;
        }
    }


}