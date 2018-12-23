package cn.dyhack.barvisual.resp;

public class AgeCount {
	
	private long age;
    private long count;
    
    public AgeCount()
    {
        
    }
    public AgeCount(long age,long count)
    {
        this.age = age;
        this.count = count;
    }
    
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	public void increase() {
		this.count = this.count + 1;
	}
	
}