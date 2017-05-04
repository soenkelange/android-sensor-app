package de.haw_hamburg.sensorapp.recorder;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.csv.CSVWriter;
import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;
import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.SensorEventsCSVRecorder;
import de.haw_hamburg.sensorapp.sensor.csv.AccelerometerSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.AmbientTemperatureSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.GameRotationVectorSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.GeomagneticRotationVectorSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.GravitySensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.GyroscopeSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.GyroscopeUncalibratedSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.LightSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.LinearAccelerationSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.MagneticFieldSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.MagneticFieldUncalibratedSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.OrientationSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.PresureSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.ProximitySensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.RelativeHumiditySensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.RotationVectorSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.TemperatureSensorEventCSVWriter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by s.lange on 29.12.16.
 */

public class RecorderPresenter extends AbstractPresenter<RecorderView> {

    private static final String TAG = RecorderPresenter.class.getSimpleName();
    private static final List<SensorEventCSVWriter> SENSOR_EVENTS_CSV_WRITER = new ArrayList<>();

    private final GetEnabledSensorInteractor enabledSensorInteractor;
    private final RxSensorManager rxSensorManager;

    private Subscription sensorSubscription;
    private boolean isRecording = false;
    private File file;
    private FileWriter fileWriter;

    static {
        SENSOR_EVENTS_CSV_WRITER.add(new AccelerometerSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new AmbientTemperatureSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new GameRotationVectorSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new GeomagneticRotationVectorSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new GravitySensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new GyroscopeSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new GyroscopeUncalibratedSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new LightSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new LinearAccelerationSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new MagneticFieldSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new MagneticFieldUncalibratedSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new OrientationSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new PresureSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new ProximitySensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new RelativeHumiditySensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new RotationVectorSensorEventCSVWriter());
        SENSOR_EVENTS_CSV_WRITER.add(new TemperatureSensorEventCSVWriter());
    }

    @Inject
    public RecorderPresenter(GetEnabledSensorInteractor getEnabledSensorInteractor, RxSensorManager rxSensorManager) {
        this.enabledSensorInteractor = getEnabledSensorInteractor;
        this.rxSensorManager = rxSensorManager;
        ;
    }

    @Override
    public void attachView(RecorderView view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (sensorSubscription != null && !sensorSubscription.isUnsubscribed()) {
            sensorSubscription.unsubscribe();
        }
    }

    public void initializeView() {
        getView().showStartButton();
    }

    public void onStartClicked() {
        startRecording();
    }

    public void onStopClicked() {
        stopRecording();
    }

    public void onSettingsClicked() {
        if (isRecording) {
            getView().showStopRecordingDialog();
        } else {
            getView().showRecorderSettings();
        }
    }

    private void startRecording() {
        getView().hideStartButton();
        getView().showStopButton();
        isRecording = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
        String todayDate = dateFormat.format(new Date());
        file = new File(Environment.getExternalStorageDirectory(), "Record-" + todayDate + ".csv");
        fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVWriter csvWriter = new CSVWriter(fileWriter);
        SensorEventsCSVRecorder sensorEventsCSVRecorder = new SensorEventsCSVRecorder(csvWriter);
        sensorSubscription = enabledSensorInteractor.execute()
                .flatMapIterable(new Func1<List<Sensor>, Iterable<Sensor>>() {
                    @Override
                    public Iterable<Sensor> call(List<Sensor> sensors) {
                        return sensors;
                    }
                })
                .map(sensor -> {
                    SensorEventCSVWriter sensorEventsCSVWriter = getSensorEventsCSVWriter(sensor);
                    if (sensorEventsCSVWriter != null) {
                        sensorEventsCSVRecorder.addSensorEventCSVWriter(sensorEventsCSVWriter);
                    }
                    return sensor;
                })
                .toList()
                .flatMapIterable(new Func1<List<Sensor>, Iterable<Sensor>>() {
                    @Override
                    public Iterable<Sensor> call(List<Sensor> sensors) {
                        return sensors;
                    }
                })
                .flatMap(new Func1<Sensor, Observable<SensorEvent>>() {
                    @Override
                    public Observable<SensorEvent> call(Sensor sensor) {
                        return rxSensorManager.observeSensorEvents(sensor.getType(), SensorManager.SENSOR_DELAY_NORMAL);
                    }
                })
                .subscribe(sensorEventsCSVRecorder::write);
    }

    private SensorEventCSVWriter getSensorEventsCSVWriter(Sensor sensor) {
        for (SensorEventCSVWriter sensorEventWriter : SENSOR_EVENTS_CSV_WRITER) {
            if (sensor.getType() == sensorEventWriter.getType()) {
                return sensorEventWriter;
            }
        }
        return null;
    }

    private void stopRecording() {
        sensorSubscription.unsubscribe();
        try {
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getView().exportCSV(Uri.fromFile(file));
        file = null;
        isRecording = false;
        getView().hideStopButton();
        getView().showStartButton();
    }
}
