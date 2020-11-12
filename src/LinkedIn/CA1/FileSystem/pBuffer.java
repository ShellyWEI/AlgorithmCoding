package LinkedIn.CA1.FileSystem;

public abstract class pBuffer {

    protected final int BLOCK_SIZE = 1024;
    protected final int BLOCK_COUNT = 1024;
    /*
    * A sample 1mb buffer, to be allocated in 1k chunks. Other sizes are possible
    * How do things change if it's a 1GB buffer?
    * */
    protected byte[] buffer = new byte[BLOCK_COUNT * BLOCK_SIZE];

    public pBuffer() {
        /*
        * Reads the buffer from file and dumps the contents into the array,
        * restoring the state to what it was when onShutDown() was called.
        * */
        //fillBufferFromFile();
    }
    // returns a Location for a free block of the buffer, suitable for passing to put, get, and free
    public abstract Location create() throws NoAvailableSpaceException;

    // Store the BLOCK_SIZE bytes of data in location l, data beyond BLOCK_SIZE should be truncated
    public abstract void put(Location l, byte[] data);

    // Store up to BLOCK_SIZE bytes of data in location l, or null if l is not located
    public abstract byte[] get(Location l);

    // Indicates that an area of the buffer is no longer needed, and can be reused
    public abstract void free(Location l);

    private void onShutDown() {
        /*
        * write full contents of the buffer to the disk,
        * for reading when later invoked by the constructor
        * */
        //writeBufferTofile();
    }
}
