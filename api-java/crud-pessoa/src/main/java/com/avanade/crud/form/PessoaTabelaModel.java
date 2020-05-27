package com.avanade.crud.form;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.avanade.db.model.PessoaModel;

public class PessoaTabelaModel extends AbstractTableModel {

	private static final long serialVersionUID = -431271233561942691L;
	
	private List<PessoaModel> pessoa = new ArrayList<PessoaModel>();
	    private String[] columnNames =  {"codigo", "nome","endereco"};

	
	    public PessoaTabelaModel() {}

	    public PessoaTabelaModel(List<PessoaModel> userData) {
	        this.pessoa = userData;
	    }

	    @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }

	    @Override
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    @Override
	    public int getRowCount() {
	        return pessoa.size();
	    }
	    

	    @Override
	    public Object getValueAt(int row, int column) {
	        Object userAttribute = null;
	        PessoaModel userObject = pessoa.get(row);
	        switch(column) {
	            case 0: userAttribute = userObject.getCodigo(); break;
	            case 1: userAttribute = userObject.getNome(); break;
	            case 2: userAttribute = userObject.getEndereco(); break;

	            default: break;
	        }
	        return userAttribute;
	    }

	    public void addUser(PessoaModel user) {
	        pessoa.add(user);
	        fireTableDataChanged();
	    }
	}