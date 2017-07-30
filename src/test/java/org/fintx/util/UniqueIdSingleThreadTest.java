package org.fintx.util;

import java.util.HashSet;
import java.util.Set;

import org.fintx.util.UniqueId;
import org.junit.Assert;
import org.junit.Test;

public class UniqueIdSingleThreadTest {

    @Test
    public void test1() {
        // check length
        String uniqueId20 = null;
        int count = 100000;
        for (int i = 0; i < count; i++) {
            uniqueId20 = UniqueId.get().toBase64String();
            Assert.assertTrue("not 20 character id:" + uniqueId20, 20 == uniqueId20.length());
        }

        // check performance single thread
        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            uniqueId20 = UniqueId.get().toBase64String();
        }
        long end = System.currentTimeMillis();
        System.out.println("Base64 ID generation total milliseconds:" + (end - begin) + " total seconds:" + (end - begin) / 1000);
        System.out.println("Base64 ID generation QPS:" + count * 1000L / ((end - begin + 1)));

        // check encode decode safety
        String uniqueId30 = null;
        UniqueId uniqueId = null;
        for (int i = 0; i < count; i++) {
            uniqueId = UniqueId.get();
            uniqueId30 = uniqueId.toHexString();
            System.out.println(uniqueId30);
            uniqueId20 = uniqueId.toBase64String();
            System.out.println(uniqueId20);
            Assert.assertTrue("Unsafe Base64 encode and decode, original id:" + i + " " + uniqueId30,
                    uniqueId30.equals(UniqueId.fromBase64String(uniqueId20).toHexString()));
            Assert.assertTrue("Unsafe Base64 encode and decode, original id:" + i + " " + uniqueId30,
                    UniqueId.fromHexString(uniqueId30).getTimestamp() == UniqueId.fromBase64String(uniqueId20).getTimestamp());
            Assert.assertTrue("Unsafe Base64 encode and decode, original id:" + i + " " + uniqueId30,
                    UniqueId.fromHexString(uniqueId30).getMachineIdentifier() == UniqueId.fromBase64String(uniqueId20).getMachineIdentifier());
            Assert.assertTrue("Unsafe Base64 encode and decode, original id:" + i + " " + uniqueId30,
                    UniqueId.fromHexString(uniqueId30).getProcessIdentifier() == UniqueId.fromBase64String(uniqueId20).getProcessIdentifier());
            Assert.assertTrue("Unsafe Base64 encode and decode, original id:" + i + " " + uniqueId30,
                    UniqueId.fromHexString(uniqueId30).getCounter() == UniqueId.fromBase64String(uniqueId20).getCounter());
        }

        Set<String> set = new HashSet<String>(count + 2);
        for (int i = 0; i < count; i++) {

            uniqueId20 = UniqueId.get().toBase64String();
            set.add(uniqueId20);
        }
        int size = set.size();
        set.clear();
        Assert.assertTrue("Duplicated key found in originalId set." + uniqueId20, size == count);

    }

}
