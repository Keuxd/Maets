package download;

public class SizeScaleManager {
	
	private long bytes;
	private long totalSizeInBytes;
	
	public SizeScaleManager(long totalSizeInBytes) {
		this.bytes = 0;
		this.totalSizeInBytes = totalSizeInBytes;
	}
	
	protected void add(int bytesAdded) {
		bytes += bytesAdded;
	}
	
	protected void add(long bytesAdded) {
		bytes += bytesAdded;
	}
	
	protected void setBytes(long bytes) {
		this.bytes = bytes;
	}
	
	protected boolean isFinished() {
		if(totalSizeInBytes == bytes) return true;
		else return false;
	}
	
	@Override
	public String toString() {
		long bytes = this.bytes;
		int kb = 0;
		int mb = 0;
		int gb = 0;
		
		long bTimes = bytes / 1000;
		for(int i = 0; i < bTimes; i++)
			kb++;
		bytes -= (bTimes * 1000);
		
		int kbTimes = kb / 1000;
		for(int i = 0; i < kbTimes; i++)
			mb++;
		kb -= (kbTimes * 1000);
		
		int mbTimes = mb / 1000;
		for(int i = 0; i < mbTimes; i++)
			gb++;
		
		mb -= (mbTimes * 1000);
		
		double percentage = (this.bytes * 100.0) / totalSizeInBytes;
		
		return String.format("(%.2f %%) GB: %d | MB: %d | KB: %d | B: %d", percentage, gb, mb, kb, bytes);
	}
}