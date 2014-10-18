package org.atomicframework.filterwheel;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public abstract class AbstractFilterStream extends ServletOutputStream {
    protected boolean closed = false;
    protected HttpServletResponse response = null;
    protected OutputStream baseStream = null;

    public AbstractFilterStream (HttpServletResponse response, OutputStream base) {
        super();
        closed = false;
        this.response = response;
        this.baseStream = base;}

    protected abstract OutputStream getBaseStream ();

    protected HttpServletResponse getResponse () {
        return response;}

    public boolean isClosed () {
        return closed;}
  
    @Override
    public void close () throws IOException {
        if (closed) throw new IOException("This output stream has already been closed");
        getBaseStream().close();
        closed = true;}

    @Override
    public void flush () throws IOException {
        if (closed) throw new IOException("Cannot flush a closed output stream");
        getBaseStream().flush();}

    @Override
    public void write (int b) throws IOException {
        if (closed) throw new IOException("Cannot write to a closed output stream");
        getBaseStream().write((byte)b);}

    @Override
    public void write (byte b[], int off, int len) throws IOException {
        if (closed) throw new IOException("Cannot write to a closed output stream");
        getBaseStream().write(b, off, len);}

    @Override
    public boolean isReady () {return true;}

    @Override
    public void setWriteListener (WriteListener listener) {}}
