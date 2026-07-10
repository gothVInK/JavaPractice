package org.example.ui;

import org.example.dao.common.IEntityRepository;
import org.example.entity.common.Column;
import org.example.entity.common.IEntity;
import org.example.entity.common.NonEditColumn;
import org.example.entity.common.SequenceColumn;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class EntityEditor<T extends IEntity> extends JDialog {
    private T entity;

    private boolean confirmed = false;
    private final Map<String, Component> fieldComponents = new HashMap<>();

    public EntityEditor(JFrame parent, String title, Class<T> entityClass, IEntityRepository<T> repository, boolean isNew, T selectedEntity) {
        super(parent, title, true);

        setLayout(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Через рефлексию получаем все поля сущности и создаем для них поля ввода
        java.util.List<Field> fields = IEntityRepository.getFieldsWithAnnotation(entityClass, Column.class);
        java.util.Map<String, String> inputMap = new java.util.HashMap<>();

        Map<String, String> predefinedValues;
        try {
            predefinedValues = IEntityRepository.getColumnsStringValue(selectedEntity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        for (Field field : fields) {
            field.setAccessible(true);

            // Пропускаем поля, значения которых генерируеются Sequence'ом
            if (isNew && field.isAnnotationPresent(SequenceColumn.class)) {
                continue;
            }

            String label = IEntityRepository.getColumnTitle(field);
            fieldsPanel.add(new JLabel(label + ":"));

            Component fieldComponent;
            if (field.getType().equals(LocalDate.class)) {
                UtilDateModel model = new UtilDateModel();
                if (predefinedValues != null) {
                    String value = predefinedValues.get(label);
                    if (value != null && !value.isEmpty()) {
                        LocalDate date = LocalDate.parse(value);
                        model.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                        model.setSelected(true);
                    }
                }

                Properties p = new Properties();
                p.put("text.today", "Сегодня");
                p.put("text.month", "Месяц");
                p.put("text.year", "Год");

                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                fieldComponent = datePicker;

                if (!isNew && field.isAnnotationPresent(NonEditColumn.class)) {
                    datePicker.setTextEditable(false);
                }
            } else {
                JTextField textField = new JTextField();
                fieldComponent = textField;

                if (!isNew && field.isAnnotationPresent(NonEditColumn.class)) {
                    textField.setEditable(false);
                }

                if (predefinedValues != null) {
                    textField.setText(predefinedValues.get(label));
                }
            }

            fieldsPanel.add(fieldComponent);
            fieldComponents.put(field.getName(), fieldComponent);
        }

        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            java.util.Map<String, String> values = new java.util.HashMap<>();
            for (java.util.Map.Entry<String, Component> entry : fieldComponents.entrySet()) {
                String text = "";
                if (entry.getValue() instanceof JTextField) {
                    text = ((JTextField)entry.getValue()).getText();
                } else if (entry.getValue() instanceof JDatePicker) {
                    Date date = (Date)((JDatePicker)entry.getValue()).getModel().getValue();
                    LocalDate localeDate = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                    text = localeDate != null ? localeDate.toString() : null;
                }
                values.put(entry.getKey(), text);
            }

            try {
                entity = (T)entityClass.getConstructor(Map.class).newInstance(values);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }

            confirmed = true;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public T getEntity() {
        return entity;
    }
}