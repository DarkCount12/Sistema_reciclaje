package Frontend.Components;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.WaypointPainter;
//import org.jxmapviewer.viewer.GeoPosition;


import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

public class MapSelectorPanel extends JPanel {

    private final JXMapViewer mapViewer;
    private GeoPosition selectedPosition;
    private MapClickListener clickListener;

    public MapSelectorPanel() {
        setLayout(new BorderLayout());

        mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));

        mapViewer.setZoom(4);
        mapViewer.setAddressLocation(new GeoPosition(-17.3936, -66.1568)); // Cochabamba

        // Habilitar arrastrar (pan)
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);

        // Habilitar zoom con rueda del mouse
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        // Seleccionar punto con clic y pintar waypoint
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point punto = e.getPoint();
                selectedPosition = mapViewer.convertPointToGeoPosition(punto);
                repaint();

                Waypoint waypoint = new DefaultWaypoint(selectedPosition);
                WaypointPainter<Waypoint> painter = new WaypointPainter<>();
                painter.setWaypoints(Collections.singleton(waypoint));
                mapViewer.setOverlayPainter(painter);

                if (clickListener != null) {
                    clickListener.onMapClick(selectedPosition);
                }
            }
        });

        add(mapViewer, BorderLayout.CENTER);
        setPreferredSize(new Dimension(600, 400));
    }

    public double getLatitudSeleccionada() {
        return selectedPosition != null ? selectedPosition.getLatitude() : 0;
    }

    public double getLongitudSeleccionada() {
        return selectedPosition != null ? selectedPosition.getLongitude() : 0;
    }

    // Interface para manejar clics en el mapa
    public interface MapClickListener {
        void onMapClick(GeoPosition position);
    }

    public void setMapClickListener(MapClickListener listener) {
        this.clickListener = listener;
    }
}
