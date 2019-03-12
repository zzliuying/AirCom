package core.structure;

import java.io.UnsupportedEncodingException;

public interface IElement {

	public abstract boolean parse(String str);

	public abstract String build()  throws UnsupportedEncodingException;

	public abstract boolean equals(Object obj);

}