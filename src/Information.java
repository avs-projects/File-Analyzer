
public class Information {
	private String name;
	private String mime;
	private String ext;
	private	long size;
	private String info;
	
	public Information(String name, String mime, String ext, long size, String info) {
		this.name = name;
		this.mime = mime;
		this.ext = ext;
		this.size = size;
		this.info = info;
	}
	
	public String toString() {
		return "Information [name=" + name + ", mime=" + mime + ", size=" + size + ", ext=" + ext + "]\n" + "Info : " + info +"\n" ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}