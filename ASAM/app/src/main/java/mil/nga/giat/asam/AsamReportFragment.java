package mil.nga.giat.asam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.commons.lang3.StringUtils;

import mil.nga.giat.asam.map.OfflineMap;
import mil.nga.giat.asam.model.AsamBean;
import mil.nga.giat.asam.util.AsamConstants;


public class AsamReportFragment extends Fragment implements OnMapReadyCallback {

    private AsamBean mAsam;
    private TextView mOccurrenceDateUI;
    private TextView mAggressorUI;
    private TextView mVictimUI;
    private TextView mSubregionUI;
    private TextView mReferenceNumberUI;
    private TextView mLocationUI;
    private TextView mDescriptionUI;

    private MapView mapView;
    private int mMapType;
    private OfflineMap offlineMap;
    private GoogleMap map;

    private Marker reportMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.asam_report_fragment, container, false);

        mOccurrenceDateUI = (TextView) view.findViewById(R.id.asam_report_fragment_occurrence_date_ui);
        mAggressorUI = (TextView) view.findViewById(R.id.asam_report_fragment_aggressor_ui);
        mVictimUI = (TextView) view.findViewById(R.id.asam_report_fragment_victim_ui);
        mSubregionUI = (TextView) view.findViewById(R.id.asam_report_fragment_subregion_ui);
        mReferenceNumberUI = (TextView) view.findViewById(R.id.asam_report_fragment_reference_number_ui);
        mLocationUI = (TextView) view.findViewById(R.id.asam_report_fragment_location_ui);
        mDescriptionUI = (TextView) view.findViewById(R.id.asam_report_fragment_description_ui);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        updateMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void updateContent(AsamBean asam) {
        mAsam = asam;

        // Sometimes eye sore if there is no entry. Just make a single " ".
        mOccurrenceDateUI.setText(AsamBean.OCCURRENCE_DATE_FORMAT.format(mAsam.getOccurrenceDate()));
        mAggressorUI.setText(StringUtils.isBlank(mAsam.getAggressor()) ? " " : mAsam.getAggressor());
        mVictimUI.setText(StringUtils.isBlank(mAsam.getVictim()) ? " " : mAsam.getVictim());
        mSubregionUI.setText(StringUtils.isBlank(mAsam.getGeographicalSubregion()) ? " " : mAsam.getGeographicalSubregion());
        mReferenceNumberUI.setText(StringUtils.isBlank(mAsam.getReferenceNumber()) ? " " : mAsam.getReferenceNumber());
        mLocationUI.setText(mAsam.formatLatitutdeDegMinSec() + ", " + mAsam.formatLongitudeDegMinSec());
        mDescriptionUI.setText(StringUtils.isBlank(mAsam.getDescription()) ? " " : mAsam.getDescription());

        updateMap();
    }

    private void updateMap() {
        if (map != null) {
            map.clear();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int mapType = preferences.getInt(AsamConstants.MAP_TYPE_KEY, GoogleMap.MAP_TYPE_NORMAL);
            if (mMapType != mapType) setMapType(mapType);

            if (reportMarker != null) {
                reportMarker.remove();
                reportMarker = null;
            }

            if (mAsam != null) {
                LatLng latLng = new LatLng(mAsam.getLatitude(), mAsam.getLongitude());
                reportMarker = map.addMarker(new MarkerOptions().position(latLng).icon(AsamConstants.ASAM_MARKER).anchor(0.5f, 1.0f));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4));
            }
        }
    }

    private void setMapType(int mapType) {
        mMapType = mapType;

        // Change the map
        if (mapType == AsamConstants.MAP_TYPE_OFFLINE) {
            if (offlineMap != null) offlineMap.clear();

            offlineMap = new OfflineMap(getActivity(), map);
        } else {
            if (offlineMap != null) {
                offlineMap.clear();
                offlineMap = null;
            }

            map.setMapType(mapType);
        }
    }

    public AsamBean getAsam() {
        return mAsam;
    }
}
