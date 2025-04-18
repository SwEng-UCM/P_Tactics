package PTactics.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class History {
	private ControllerInterface _ctrl;
	private static History _record;
	
	//Public attribute, filepath should be checked before assigning it here.
	private File _outFile;
	private File _inFile;
	
	private History (ControllerInterface _cntr) {
		_inFile = new File("examples/JSONtest.json");
		_outFile = new File("examples/JSONtest.json");
		_ctrl = _cntr;
	}
	
	public static History getInstance(ControllerInterface CI) {
		if(_record == null) {
			_record = new History(CI);
		}
		
		return _record;
	}
	
	public void save() {
		try {
            FileWriter myWriter = new FileWriter(_outFile);
            myWriter.write(_ctrl.report().toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void load() {
		if (_inFile != null) {
			try {
				InputStream is = new FileInputStream(_inFile);
				_ctrl.load(is);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setOutFile(String path) {
		_outFile = new File(path);
	}
	
	public void setInFile(String path) {
		_inFile = new File(path);
	}
}
