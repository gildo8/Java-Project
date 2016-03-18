/**
 * @Project: RemoteServer
 * @Class : ClassInputDialog.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * The Class Input Dialog gets a type of class and builds
 * Dialog Window that show all it's members.
 */
public class ClassInputDialog extends Dialog {
	
	/** The template. */
	private Class<?> template;
	
	/** The descs. */
	PropertyDescriptor[] descs;
	
	/** The txt map. */
	HashMap<PropertyDescriptor,Text> txtMap = new HashMap<PropertyDescriptor,Text>();
	
	/** The enum map. */
	HashMap<PropertyDescriptor,String> enumMap = new HashMap<PropertyDescriptor,String>();

	  /** The input. */
	private Object input;

	  /**
	 * Instantiates a new class input dialog.
	 *
	 * @param parent
	 *            the parent
	 * @param template
	 *            the template
	 */
	public ClassInputDialog(Shell parent,Class<?> template) {
		    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,template);
		  }

	  /**
	 * Instantiates a new class input dialog.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param template
	 *            the template
	 */
	public ClassInputDialog(Shell parent, int style,Class<?> template) {
	    super(parent, style);
	    this.template=template;
		descs=PropertyUtils.getPropertyDescriptors(template);
	    setText("Maze Properties");
	  }


	  /**
	 * Gets the input.
	 *
	 * @return the input
	 */
  	public Object getInput() {
	    return input;
	  }

	  /**
	 * Sets the input.
	 *
	 * @param input
	 *            the new input
	 */
  	public void setInput(Object input) {
	    this.input = input;
	  }

	  /**
	 * Open.
	 *
	 * @return the object
	 */
	public Object open() {
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setText(getText());
//	    Image pic = new Image(null, ".\\resources\\images\\image.jpg");
//	    shell.setBackgroundImage(pic);
	    Color backColor = new Color(null, 0,0,255);
	    shell.setBackground(backColor);
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    //display.dispose();
	    return input;
	  }

	  /**
	 * Creates the contents of The member's class/Enum.
	 *
	 * @param shell
	 *            the shell
	 */
	private void createContents(final Shell shell) {
	    shell.setLayout(new GridLayout(2, false));

	    for(PropertyDescriptor propDesc: descs)
	    	if(!propDesc.getName().equals("class"))
	    	{
	    		if(!propDesc.getPropertyType().isEnum())
	    		{
	    			Label label = new Label(shell, SWT.NONE | SWT.NO_BACKGROUND);
	    			Color c = new Color(null, 0, 0, 255);
	    			label.setBackground(c);
	    			label.setForeground(label.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		    		FontData fontData = label.getFont().getFontData()[0];
//		    		fontData.setHeight(30);
		    		Font font = new Font(label.getDisplay(), new FontData(fontData.getName(), fontData
		    			    .getHeight(),SWT.BOLD)); 
		    	    label.setFont(font);
	   
		    	    label.setText(propDesc.getDisplayName());
		    	    GridData data = new GridData();
		    	    data.horizontalSpan = 2;
		    	    label.setLayoutData(data);

		    	    final Text text = new Text(shell, SWT.BORDER);
		    	    data = new GridData(GridData.FILL_HORIZONTAL);
		    	    data.horizontalSpan = 2;
		    	    text.setLayoutData(data);
		    	    
		    	    //for the text field
		    	    text.setForeground(text.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		    		FontData fontDataText = text.getFont().getFontData()[0];
//		    		fontData.setHeight(30);
		    		Font fontText = new Font(text.getDisplay(), new FontData(fontDataText.getName(), fontData
		    			    .getHeight(), SWT.BOLD)); 
		    	    text.setFont(fontText);
		    	    Color textFieldColor = new Color(null,0,255,255);
		    	    text.setBackground(textFieldColor);
		    	    
		    	    txtMap.put(propDesc, text);
	    		}
	    		else
	    		{
	    			Label label = new Label(shell, SWT.NONE | SWT.NO_BACKGROUND);
	    			label.setBackground(null);
	    			label.setForeground(label.getDisplay().getSystemColor(SWT.COLOR_WHITE));
	    			Color c = new Color(null, 0, 0, 255);
	    			label.setBackground(c);
	    			
		    		FontData fontData = label.getFont().getFontData()[0];
//		    		fontData.setHeight(30);
		    		Font font = new Font(label.getDisplay(), new FontData(fontData.getName(), fontData
		    			    .getHeight(), SWT.BOLD)); 
		    	    label.setFont(font);
	    			
		    	    label.setText(propDesc.getName());
		    	    GridData data = new GridData();
		    	    data.horizontalSpan = 2;
		    	    label.setLayoutData(data);
	    			
		    	    final Combo combo = new Combo(shell, SWT.SINGLE);
//		    	    final Combo combo = new Combo(shell	, SWT.RADIO);
		    
		    	    String[] toCombo=new String[propDesc.getPropertyType().getEnumConstants().length];
		    	    for(int i=0;i<propDesc.getPropertyType().getEnumConstants().length;i++)
		    	    	toCombo[i]=propDesc.getPropertyType().getEnumConstants()[i].toString();
		    	    combo.setItems(toCombo);
		    	    data = new GridData(GridData.FILL_HORIZONTAL);
		    	    data.horizontalSpan = 2;
		    	    
		    	    Color c1 = new Color(null,0, 255, 255);
		    	    combo.setBackground(c1);
		    	    combo.setForeground(combo.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		    	    combo.setLayoutData(data);
		    		FontData fontDataCombo = combo.getFont().getFontData()[0];
//		    		fontData.setHeight(30);
		    		Font fontCombo = new Font(combo.getDisplay(), new FontData(fontDataCombo.getName(), fontData
		    			    .getHeight(),SWT.BOLD)); 
		    	    combo.setFont(fontCombo);
		    	    
		    	    combo.addSelectionListener(new SelectionListener() {
						
						@Override
						public void widgetSelected(SelectionEvent arg0) {
			    	        enumMap.put(propDesc, combo.getText());
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
							//Auto-generated method stub	
						}
					});
		    	   
	    		}
	    		
	    	}
	    /*Label label = new Label(shell, SWT.NONE);
	    label.setText(message);
	    GridData data = new GridData();
	    data.horizontalSpan = 2;
	    label.setLayoutData(data);

	    final Text text = new Text(shell, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 2;
	    text.setLayoutData(data);*/

	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("Submit");
	    GridData dataOK = new GridData(GridData.FILL_HORIZONTAL);
	    ok.setLayoutData(dataOK);
	    ok.addSelectionListener(new SelectionAdapter() {
	    	
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public void widgetSelected(SelectionEvent event) {

	    	Object t;
			try {
				t = template.newInstance();
				for(PropertyDescriptor propDesc: txtMap.keySet())
		  		{
		  			if(isNumeric(txtMap.get(propDesc).getText()))
		  				PropertyUtils.setNestedProperty(t, propDesc.getName(), Integer.parseInt(txtMap.get(propDesc).getText()));
		  			else
		  				PropertyUtils.setNestedProperty(t, propDesc.getName(), txtMap.get(propDesc).getText());
		  		}
				for(PropertyDescriptor propDesc : enumMap.keySet())
				{
		    	    
		    	    PropertyUtils.setNestedProperty(t, propDesc.getName(), Enum.valueOf((Class<Enum>)propDesc.getPropertyType(),enumMap.get(propDesc)));
				}
		  		input=t;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.toString();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
	  		
	        shell.close();
	      }
	    });

	    Button cancel = new Button(shell, SWT.PUSH | SWT.NO_BACKGROUND);
	    cancel.setText("Cancel");
	    GridData dataCancel = new GridData(GridData.FILL_HORIZONTAL);
	    cancel.setLayoutData(dataCancel);
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });

	    shell.setDefaultButton(ok);
	  }
	  
  	/**
		 * Checks if is numeric.
		 *
		 * @param str
		 *            the str
		 * @return true, if is numeric
		 */
	private static boolean isNumeric(String str)
	  {
	    NumberFormat formatter = NumberFormat.getInstance();
	    ParsePosition pos = new ParsePosition(0);
	    formatter.parse(str, pos);
	    return str.length() == pos.getIndex();
	  }
}

