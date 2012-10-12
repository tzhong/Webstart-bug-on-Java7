/*
 * Copyright (c) 1995, 2009, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package webstartComponentArch;

/*
 * This code is based on an example provided by Richard Stanford, 
 * a tutorial reader.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

public class DynamicTreePanel extends JPanel 
                             implements ActionListener {
    private static String ADD_COMMAND = "add";
    private static String CLEAR_COMMAND = "clear";
    
    private DynamicTree treePanel;

    public DynamicTreePanel() {
        super(new BorderLayout());
        
        //Create the components.
        treePanel = new DynamicTree();
        populateTree(treePanel);

        JButton addButton = new JButton("Find nativelib");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);
        
        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand(CLEAR_COMMAND);
        clearButton.addActionListener(this);

        //Lay everything out.
        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0,3));
        panel.add(addButton);
        panel.add(clearButton);
	add(panel, BorderLayout.SOUTH);
    }

    public void populateTree(DynamicTree treePanel) {
        String p1Name = new String("Parent 1");
        String p2Name = new String("Parent 2");
        String c1Name = new String("Child 1");
        String c2Name = new String("Child 2");

        DefaultMutableTreeNode p1, p2;

        p1 = treePanel.addObject(null, p1Name);
        p2 = treePanel.addObject(null, p2Name);

        treePanel.addObject(p1, c1Name);
        treePanel.addObject(p1, c2Name);

        treePanel.addObject(p2, c1Name);
        treePanel.addObject(p2, c2Name);
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String lib = "jacob-1.16-M1-x64";
        Class<DynamicTreePanel> clz = DynamicTreePanel.class;
        java.lang.ClassLoader ldr = clz.getClassLoader();
        
       
        URL url;
        
    	url = ldr.getResource(lib + ".dll");
        
    	String buf = "";
        if (url != null) {
        	buf = "jacob.dll retrieved";
        } else {
        	buf = "jacob.dll not found";
        }
    	buf = buf + " (JRE version: " + System.getProperty("java.version") + ")";
    	treePanel.addObject(buf);
        
    	url = ldr.getResource("webstartComponentArch/" + lib + ".dll");
        
        if (url != null) {
        	treePanel.addObject("webstartComponentArch/jacob.dll");
        }
        
        if (ADD_COMMAND.equals(command)) {
        } else if (CLEAR_COMMAND.equals(command)) {
            //Clear button clicked.
            treePanel.clear();
        }
    }
}
