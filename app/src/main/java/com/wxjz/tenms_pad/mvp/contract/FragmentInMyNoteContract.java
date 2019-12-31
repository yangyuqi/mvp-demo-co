package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.NoteBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/17.
 */

public interface FragmentInMyNoteContract {
    interface Presenter {
        void getMyNotes(String userId, Integer subId, int domType, int page, int rows, int stuLevelId);

        void deleteNotes(String ids, int domType);

        void getPlayAuthByVid(String vid);
    }

    interface View extends IBaseView {
        void onMyNotes(List<NoteBean> noteBeans);
        void onMyNotesMore(List<NoteBean> noteBeans);
        void onDeleteNotes();
        void onPlayAuth(PlayAuthBean playAuthBean);
    }
}
