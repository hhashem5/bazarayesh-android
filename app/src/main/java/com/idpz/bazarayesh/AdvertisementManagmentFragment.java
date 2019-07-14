package com.idpz.bazarayesh;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idpz.bazarayesh.Adapters.DialogItemAdapter;
import com.idpz.bazarayesh.Advertisements.BazdidAroosActivity;
import com.idpz.bazarayesh.Advertisements.EstekhdamActivity;
import com.idpz.bazarayesh.Advertisements.KargahAmozeshi;
import com.idpz.bazarayesh.Advertisements.SabtnamDoreAmozeshi;
import com.idpz.bazarayesh.Advertisements.TakhfifAmozeshi;
import com.idpz.bazarayesh.Advertisements.TakhfifKhadamat;
import com.idpz.bazarayesh.Advertisements.VagozariArayeshgah;
import com.idpz.bazarayesh.Models.MainItem;
import com.idpz.bazarayesh.Utils.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.idpz.bazarayesh.BaseActivity.RIGHT_TO_LEFT;
import static com.idpz.bazarayesh.SubProfileActivity.tag;
import static com.idpz.bazarayesh.Utils.AppController.getAppContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class AdvertisementManagmentFragment extends BaseFragment implements View.OnClickListener {


    CardView card3, card2, card1;

    List<MainItem> items;

    Tools tools;

    String cardTag;

    String memberId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.advertisement_managment_fragment, container, false);
        tools = new Tools(getContext());

        initViews(v);


        switch (tag) {
            case 1:
                if (!tools.getSharePrf("memberId1").equals("")) {

                    memberId = tools.getSharePrf("memberId1");
                }
                break;

            case 2:

                if (!tools.getSharePrf("memberId2").equals("")) {

                    memberId = tools.getSharePrf("memberId2");
                }

                break;

            case 3:

                if (!tools.getSharePrf("memberId3").equals("")) {

                    memberId = tools.getSharePrf("memberId3");
                }
                break;
            case 4:

                if (!tools.getSharePrf("memberId4").equals("")) {

                    memberId = tools.getSharePrf("memberId4");
                }

                break;
            case 5:

                if (!tools.getSharePrf("memberId5").equals("")) {


                    memberId = tools.getSharePrf("memberId5");
                }


                break;
        }

        return v;
    }

    public void initViews(View v) {
        card3 = v.findViewById(R.id.card3);
        card2 = v.findViewById(R.id.card2);
        card1 = v.findViewById(R.id.card1);


        card3.setOnClickListener(this);
        card2.setOnClickListener(this);
        card1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.card3:
                cardTag = "card3";
                switch (tag) {
                    case 1:
                        if (tools.getSharePrf("memberId1").equals("")) {
                            tools.alertShow("ابتدا باید آرایشگاه خود را ثبت کنید سپس میتوانید آگهی ثبت کنید");
                        } else {
                            memberId = tools.getSharePrf("memberId1");
                            AlertDialog();
                        }
                        break;

                    case 2:

                        if (tools.getSharePrf("memberId2").equals("")) {
                            tools.alertShow("ابتدا باید به عنوان آرایشگر ثبنام کنید سپس میتوانید آگهی ثبت کنید.");
                        } else {
                            AlertDialog();
                            memberId = tools.getSharePrf("memberId2");
                        }

                        break;

                    case 3:

                        if (tools.getSharePrf("memberId3").equals("")) {
                            tools.alertShow("ابتدا باید آموزشگاه خود را ثبت کنید سپس میتوانید آگهی ثبت کنید.");
                        } else {
                            AlertDialog();
                            memberId = tools.getSharePrf("memberId3");
                        }
                        break;
                    case 4:

                        if (tools.getSharePrf("memberId4").equals("")) {
                            tools.alertShow("ابتدا باید به عنوان مدرس ثبنام کنید سپس میتوانید آگهی ثبت کنید.");
                        } else {
                            AlertDialog();
                            memberId = tools.getSharePrf("memberId4");
                        }

                        break;
                    case 5:

                        if (tools.getSharePrf("memberId5").equals("")) {

                            tools.alertShow("ابتدا باید فروشگاه خود را ثبت کنید سپس میتوانید آگهی ثبت کنید");
                        } else {
                            AlertDialog();
                            memberId = tools.getSharePrf("memberId5");
                        }


                        break;
                }


                break;


            case R.id.card2:

                cardTag = "card2";
                AlertDialog();

                break;

            case R.id.card1:
                cardTag = "card1";
                AlertDialog();

                break;
        }

    }


    public void AlertDialog() {


        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSans(FaNum).ttf");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View inflatedLayout = getLayoutInflater().inflate(R.layout.choose_advertisement, null);

        RecyclerView recyclerView = inflatedLayout.findViewById(R.id.recycle);

        TextView title = inflatedLayout.findViewById(R.id.tooTitle);
        title.setText("دسته بندی آگهی ها");

        addItems();
        DialogItemAdapter dialogItemAdapter = new DialogItemAdapter(items, getActivity(), new DialogItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {

                MainItem item = (MainItem) object;
                if (cardTag.equals("card3")) {
                    switch (item.getTitle()) {
                        case "آگهی بازدید عروس":

                            Intent i1 = new Intent(getContext(), BazdidAroosActivity.class);
                            i1.putExtra("tag", tag);

                            i1.setAction(Intent.ACTION_MAIN);
                            i1.addCategory(Intent.CATEGORY_HOME);
                            i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i1);
                            customType(getContext(), RIGHT_TO_LEFT);
                            getActivity().finish();
                            break;

                        case "استخدام":
                            Intent i2 = new Intent(getContext(), EstekhdamActivity.class);
                            i2.putExtra("tag", tag);

                            i2.setAction(Intent.ACTION_MAIN);
                            i2.addCategory(Intent.CATEGORY_HOME);
                            i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i2);
                            customType(getContext(), RIGHT_TO_LEFT);
                            getActivity().finish();
                            break;

                        case "ثبتنام دوره های آموزشی":
                            Intent i3 = new Intent(getContext(), SabtnamDoreAmozeshi.class);
                            i3.putExtra("tag", tag);
                            i3.setAction(Intent.ACTION_MAIN);
                            i3.addCategory(Intent.CATEGORY_HOME);
                            i3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i3);
                            customType(getContext(), RIGHT_TO_LEFT);
                            getActivity().finish();
                            break;

                        case "شروع ثبتنام کارگاه آموزشی":
                            Intent i4 = new Intent(getContext(), KargahAmozeshi.class);
                            i4.putExtra("tag", tag);

                            i4.setAction(Intent.ACTION_MAIN);
                            i4.addCategory(Intent.CATEGORY_HOME);
                            i4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i4);
                            customType(getContext(), RIGHT_TO_LEFT);
                            getActivity().finish();
                            break;


                        case "تخفیف  دوره و کارگاه آموزشی":
                            Intent i5 = new Intent(getContext(), TakhfifAmozeshi.class);
                            i5.putExtra("tag", tag);

                            i5.setAction(Intent.ACTION_MAIN);
                            i5.addCategory(Intent.CATEGORY_HOME);
                            i5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i5);
                            customType(getContext(), RIGHT_TO_LEFT);
                            getActivity().finish();
                            break;

                        case "واگذاری فضای آرایشگاهی":
                            Intent i6 = new Intent(getContext(), VagozariArayeshgah.class);
                            i6.putExtra("tag", tag);

                            i6.setAction(Intent.ACTION_MAIN);
                            i6.addCategory(Intent.CATEGORY_HOME);
                            i6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i6);
                            customType(getContext(), RIGHT_TO_LEFT);
                            getActivity().finish();
                            break;

                        case "تخفیف خدمات آرایشکاهی":

                            Intent i7 = new Intent(getContext(), TakhfifKhadamat.class);
                            i7.putExtra("tag", tag);

                            i7.setAction(Intent.ACTION_MAIN);
                            i7.addCategory(Intent.CATEGORY_HOME);
                            i7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i7);
                            customType(getContext(), RIGHT_TO_LEFT);
                            getActivity().finish();
                            break;

                    }

                } else if (cardTag=="card2"){

                    switch (item.getTitle()) {
                        case "آگهی بازدید عروس":

                            Intent i1 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i1.putExtra("tag", "Bride");
                            i1.putExtra("memid", memberId);
                            i1.putExtra("ex",1);
                            startActivity(i1);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "استخدام":
                            Intent i2 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i2.putExtra("tag", "Recruiment");
                            i2.putExtra("memid", memberId);
                            i2.putExtra("ex",1);

                            startActivity(i2);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "ثبتنام دوره های آموزشی":
                            Intent i3 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i3.putExtra("tag", "Reg_Course");
                            i3.putExtra("memid", memberId);
                            i3.putExtra("ex",1);

                            startActivity(i3);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "شروع ثبتنام کارگاه آموزشی":
                            Intent i4 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i4.putExtra("tag", "Workshops");

                            i4.putExtra("memid", memberId);
                            i4.putExtra("ex",1);

                            startActivity(i4);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;


                        case "تخفیف  دوره و کارگاه آموزشی":
                            Intent i5 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i5.putExtra("tag", "Discount_ads");
                            i5.putExtra("memid", memberId);
                            i5.putExtra("ex",1);

                            startActivity(i5);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "واگذاری فضای آرایشگاهی":
                            Intent i6 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i6.putExtra("tag", "Assignment");
                            i6.putExtra("memid", memberId);
                            i6.putExtra("ex",1);

                            startActivity(i6);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "تخفیف خدمات آرایشکاهی":

                            Intent i7 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i7.putExtra("tag", "Discount_ads");
                            i7.putExtra("memid", memberId);
                            i7.putExtra("ex",1);


                            startActivity(i7);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;
                    }


                }

                else {


                    switch (item.getTitle()) {
                        case "آگهی بازدید عروس":

                            Intent i1 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i1.putExtra("tag", "Bride");
                            i1.putExtra("memid", memberId);
                            i1.putExtra("ex",0);
                            startActivity(i1);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "استخدام":
                            Intent i2 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i2.putExtra("tag", "Recruiment");
                            i2.putExtra("memid", memberId);
                            i2.putExtra("ex",0);

                            startActivity(i2);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "ثبتنام دوره های آموزشی":
                            Intent i3 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i3.putExtra("tag", "Reg_Course");
                            i3.putExtra("memid", memberId);
                            i3.putExtra("ex",0);

                            startActivity(i3);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "شروع ثبتنام کارگاه آموزشی":
                            Intent i4 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i4.putExtra("tag", "Workshops");

                            i4.putExtra("memid", memberId);
                            i4.putExtra("ex",0);

                            startActivity(i4);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;


                        case "تخفیف  دوره و کارگاه آموزشی":
                            Intent i5 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i5.putExtra("tag", "Discount_ads");
                            i5.putExtra("memid", memberId);
                            i5.putExtra("ex",0);

                            startActivity(i5);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "واگذاری فضای آرایشگاهی":
                            Intent i6 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i6.putExtra("tag", "Assignment");
                            i6.putExtra("memid", memberId);
                            i6.putExtra("ex",0);

                            startActivity(i6);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;

                        case "تخفیف خدمات آرایشکاهی":

                            Intent i7 = new Intent(getContext(), UserAdvertisementActivity.class);
                            i7.putExtra("tag", "Discount_ads");
                            i7.putExtra("memid", memberId);
                            i7.putExtra("ex",0);


                            startActivity(i7);
                            customType(getContext(), RIGHT_TO_LEFT);
                            break;
                    }


                }


            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(dialogItemAdapter);

        builder.setView(inflatedLayout);

        builder.show();
    }


    public void addItems() {

        items = new ArrayList<>();

        switch (tag) {

            case 1:
                tools.addToSharePrf("beautyshop", "1");

                items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
                items.add(new MainItem(R.drawable.ic_bride, "آگهی بازدید عروس"));
                items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی"));
                items.add(new MainItem(R.drawable.ic_noun_facial_wash, "تخفیف خدمات آرایشکاهی"));

                break;

            case 2:
                tools.addToSharePrf("hairdersser", "1");
                items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
                items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی"));

                break;
            case 3:
                tools.addToSharePrf("institude", "1");
                items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
                items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "ثبتنام دوره های آموزشی"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی"));
                items.add(new MainItem(R.drawable.ic_teaching, "تخفیف  دوره و کارگاه آموزشی"));

                break;

            case 4:
                tools.addToSharePrf("teacher", "1");
                items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
                items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "ثبتنام دوره های آموزشی"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی"));
                items.add(new MainItem(R.drawable.ic_teaching, "تخفیف  دوره و کارگاه آموزشی"));

                break;
            case 5:
                tools.addToSharePrf("store", "1");
                items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
                items.add(new MainItem(R.drawable.ic_employees, "تخفیف محصولات آرایشی"));


                break;

            default:

                items.add(new MainItem(R.drawable.ic_employees, "استخدام"));
                items.add(new MainItem(R.drawable.ic_noun_teacher, "شروع ثبتنام کارگاه آموزشی"));
                items.add(new MainItem(R.drawable.ic_clipboard_with_pencil, "ثبتنام دوره های آموزشی"));
                items.add(new MainItem(R.drawable.ic_bride, "آگهی بازدید عروس"));
                items.add(new MainItem(R.drawable.ic_teaching, "تخفیف  دوره و کارگاه آموزشی"));
                items.add(new MainItem(R.drawable.ic_door, "واگذاری فضای آرایشگاهی"));
                items.add(new MainItem(R.drawable.ic_noun_facial_wash, "تخفیف خدمات آرایشکاهی"));
                items.add(new MainItem(R.drawable.ic_facial_cream_tube, "تخفیف 3 محصولات"));
                items.add(new MainItem(R.drawable.ic_spray_bottle, "واگذاری تجهیزات آرایشگاهی"));


        }
    }


}
