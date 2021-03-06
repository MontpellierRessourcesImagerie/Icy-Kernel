/*
 * Copyright 2010-2015 Institut Pasteur.
 * 
 * This file is part of Icy.
 * 
 * Icy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Icy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Icy. If not, see <http://www.gnu.org/licenses/>.
 */
package icy.gui.menu;

import icy.plugin.PluginDescriptor;
import icy.plugin.PluginLauncher;
import icy.resource.ResourceUtil;
import icy.system.thread.ThreadUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

/**
 * This class represent a MenuItem which launch a plugin when pressed.
 */
public class PluginMenuItem extends JMenuItem implements ActionListener
{
    private static final long serialVersionUID = 2508924050709990008L;

    private static final int ICON_SIZE = 24;

    final PluginDescriptor pluginDescriptor;

    public PluginMenuItem(PluginDescriptor pluginDescriptor)
    {
        super(pluginDescriptor.getSimpleClassName());

        this.pluginDescriptor = pluginDescriptor;

        // do it in background as loading icon can take sometime
        ThreadUtil.bgRun(new Runnable()
        {
            @Override
            public void run()
            {
                final ImageIcon icon = PluginMenuItem.this.pluginDescriptor.getIcon();
                if (icon != null)
                    setIcon(ResourceUtil.scaleIcon(icon, ICON_SIZE));
            }
        });

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        PluginLauncher.start(pluginDescriptor);
    }
}
