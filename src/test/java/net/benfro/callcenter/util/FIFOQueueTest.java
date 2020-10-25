package net.benfro.callcenter.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FIFOQueueTest {

    private FIFOQueue<Integer> instance;

    @Before
    public void setUp() throws Exception {
        instance = new FIFOQueue(1);
    }

    @Test
    public void initial_size_is_zero() throws Exception {
        assertThat(instance.size(), is(0));
    }

    @Test
    public void size_increases_by_enqueue() throws Exception {
        assertThat(instance.enqueue(1), is(true));
        assertThat(instance.size(), is(1));
    }

    @Test
    public void size_decreases_by_dequeue() throws Exception {
        instance.enqueue(1);
        assertThat(instance.dequeue().get(), is(Integer.valueOf(1)));
        assertThat(instance.size(), is(0));
    }

    @Test
    public void first_in_object_returned_by_dequeue() throws Exception {
        instance = new FIFOQueue<Integer>(2);
        instance.enqueue(100);
        instance.enqueue(200);
        assertThat(instance.dequeue().get(), is(Integer.valueOf(100)));
        assertThat(instance.dequeue().get(), is(Integer.valueOf(200)));
        assertThat(instance.size(), is(0));
    }

    @Test
    public void capacity_is_respected() throws Exception {
        instance = new FIFOQueue<Integer>(2);
        assertThat(instance.enqueue(100), is(true));
        assertThat(instance.enqueue(200), is(true));
        assertThat(instance.enqueue(300), is(false));
        assertThat(instance.dequeue().get(), is(Integer.valueOf(100)));
        assertThat(instance.dequeue().get(), is(Integer.valueOf(200)));
        assertThat(instance.dequeue().isPresent(), is(false));
    }

    @Test
    public void remove_works() throws Exception {
        instance = new FIFOQueue<Integer>(20);
        instance.enqueue(100);
        instance.enqueue(200);
        instance.enqueue(300);
        instance.remove(200);
        assertThat(instance.dequeue().get(), is(Integer.valueOf(100)));
        assertThat(instance.dequeue().get(), is(Integer.valueOf(300)));
        assertThat(instance.size(), is(0));

    }

    @Test
    public void peek_works() throws Exception {
        instance = new FIFOQueue<Integer>(20);
        instance.enqueue(100);
        instance.enqueue(200);
        instance.enqueue(300);

        assertThat(instance.peek().get(), is(Integer.valueOf(100)));


    }
}