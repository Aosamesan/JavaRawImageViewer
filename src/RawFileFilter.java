import java.io.File;
import javax.swing.filechooser.FileFilter;

class RawFileFilter extends FileFilter {
	@Override
	public boolean accept(File f){
		if (f.isDirectory()){
			return true;
		}

		String ext = getExtension(f);

		if(ext != null){
			if(ext.equals("raw")){
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	@Override
	public String getDescription(){
		return "Raw Image File";
	}

	private String getExtension(File f){
		String ext = null;
		String fileName = f.getName();
		int dotIdx = fileName.lastIndexOf('.');

		if(dotIdx > 0 && dotIdx < fileName.length() - 1){
			ext = fileName.substring(dotIdx + 1).toLowerCase();
		}

		return ext;
	}
}
