package pl.pzienowicz.trialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

import java.util.Calendar;

import pl.pzienowicz.trialer.action.DisplayToastAction;
import pl.pzienowicz.trialer.action.FinishActivityAction;
import pl.pzienowicz.trialer.listener.TrialEndedListener;
import pl.pzienowicz.trialer.validator.InstallDateValidator;
import pl.pzienowicz.trialer.validator.RunTimesValidator;
import pl.pzienowicz.trialer.validator.StaticDateValidator;
import pl.pzienowicz.trialer.validator.ValidatorInterface;

/**
 * Trial test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TrialerTest {

    @Mock
    private
    Context mMockContext = mock(Context.class);

    @Mock
    private
    TrialEndedListener trialEndedListener = mock(TrialEndedListener.class);

    @Mock
    private
    PackageManager packageManager = mock(PackageManager.class);

    @Mock
    private
    PackageInfo packageInfo = mock(PackageInfo.class);

    @Mock
    private
    SharedPreferences sharedPreferences = mock(SharedPreferences.class);

    @Mock
    private
    FinishActivityAction finishActivityAction = mock(FinishActivityAction.class);

    @Mock
    private
    DisplayToastAction displayToastAction = mock(DisplayToastAction.class);

    @Test
    public void validWithoutAnyValidator() {
        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.valid();

        verify(trialEndedListener, never()).onTrialEnded(any(ValidatorInterface.class));
    }

    @Test
    public void validWithStaticDateValidator_TrialEnded() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.addValidator(new StaticDateValidator(calendar.getTime()));
        trialer.valid();

        verify(trialEndedListener, times(1)).onTrialEnded(any(StaticDateValidator.class));
    }

    @Test
    public void validWithStaticDateValidator_TrialNotEnded() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);

        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.addValidator(new StaticDateValidator(calendar.getTime()));
        trialer.valid();

        verify(trialEndedListener, never()).onTrialEnded(any(StaticDateValidator.class));
    }

    @Test
    public void validWithInstallDateValidator_TrialEnded() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);

        packageInfo.firstInstallTime = calendar.getTimeInMillis();

        try {
            when(mMockContext.getPackageName()).thenReturn("test");
            when(mMockContext.getPackageManager()).thenReturn(packageManager);
            when(packageManager.getPackageInfo("test", 0)).thenReturn(packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.addValidator(new InstallDateValidator(4));
        trialer.valid();

        verify(trialEndedListener, times(1)).onTrialEnded(any(InstallDateValidator.class));
    }

    @Test
    public void validWithInstallDateValidator_TrialNotEnded() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);

        packageInfo.firstInstallTime = calendar.getTimeInMillis();

        try {
            when(mMockContext.getPackageName()).thenReturn("test");
            when(mMockContext.getPackageManager()).thenReturn(packageManager);
            when(packageManager.getPackageInfo("test", 0)).thenReturn(packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.addValidator(new InstallDateValidator(10));
        trialer.valid();

        verify(trialEndedListener, never()).onTrialEnded(any(InstallDateValidator.class));
    }

    @Test
    public void validWithRunTimesValidator_TrialEnded() {
        when(sharedPreferences.getInt(RunTimesValidator.PREFS_NAME, 0)).thenReturn(4);
        when(mMockContext.getSharedPreferences(RunTimesValidator.PREFS_FILENAME, 0)).thenReturn(sharedPreferences);

        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.addValidator(new RunTimesValidator(4));
        trialer.valid();

        verify(trialEndedListener, times(1)).onTrialEnded(any(RunTimesValidator.class));
    }

    @Test
    public void validWithRunTimesValidator_TrialNotEnded() {
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);

        when(editor.putInt(any(String.class), any(Integer.class))).thenReturn(editor);
        when(sharedPreferences.getInt(RunTimesValidator.PREFS_NAME, 0)).thenReturn(2);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(mMockContext.getSharedPreferences(RunTimesValidator.PREFS_FILENAME, 0)).thenReturn(sharedPreferences);

        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.addValidator(new RunTimesValidator(10));
        trialer.valid();

        verify(trialEndedListener, never()).onTrialEnded(any(RunTimesValidator.class));
    }


    @Test
    public void validWithStaticDateValidator_TrialEnded_ActionsStarted() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        Trialer trialer = new Trialer(mMockContext);
        trialer.setTrialEndedListener(trialEndedListener);
        trialer.addValidator(new StaticDateValidator(calendar.getTime()));
        trialer.addAction(finishActivityAction);
        trialer.addAction(displayToastAction);
        trialer.valid();

        verify(trialEndedListener, times(1)).onTrialEnded(any(StaticDateValidator.class));
        verify(finishActivityAction, times(1)).run();
        verify(displayToastAction, times(1)).run();
    }

}
