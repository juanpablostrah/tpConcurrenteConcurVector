
public class WorkUnitSizeDTO {
	
	public int cantBlocks ;
	public int cantByBlock;
	public int cantExtraBlocks ;
	public int cantExtraByBlock;
	
	public WorkUnitSizeDTO(int cantBlocks, int cantByBlock, int cantExtraBlocks, int cantExtraByBlocks) {
		this.cantBlocks = cantBlocks;
		this.cantByBlock = cantByBlock;
		this.cantExtraBlocks = cantExtraBlocks;
		this.cantExtraByBlock = cantExtraByBlocks;
	}
	
}
